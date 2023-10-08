package com.ahrorovkspace.codebeyondearth.presentation.applicationScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahrorovkspace.codebeyondearth.core.Resource
import com.ahrorovkspace.codebeyondearth.core.states.PostApplicationRespState
import com.ahrorovkspace.codebeyondearth.data.local.DataStoreManager
import com.ahrorovkspace.codebeyondearth.domain.application.model.PostApplicationBody
import com.ahrorovkspace.codebeyondearth.domain.application.model.PostApplicationResp
import com.ahrorovkspace.codebeyondearth.domain.application.useCase.PostApplicationUseCase
import com.ahrorovkspace.codebeyondearth.domain.refreshToken.model.RefreshTokenBody
import com.ahrorovkspace.codebeyondearth.domain.refreshToken.model.RefreshTokenResp
import com.ahrorovkspace.codebeyondearth.domain.refreshToken.useCase.RefreshTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.plus
import javax.inject.Inject

@HiltViewModel
class ApplicationViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val postApplicationUseCase: PostApplicationUseCase,
    private val refreshTokenUseCase: RefreshTokenUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(ApplicationState())
    val state = _state.stateIn(
        viewModelScope + Dispatchers.IO,
        SharingStarted.WhileSubscribed(5000),
        ApplicationState()
    )

    init {
        dataStoreManager.getRefreshToken.onEach { value ->
            _state.update {
                it.copy(
                    refreshToken = value
                )
            }
        }.launchIn(viewModelScope)
        dataStoreManager.getAccessToken.onEach { value ->
            _state.update {
                it.copy(
                    accessToken = value
                )
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: ApplicationEvent) {
        when (event) {
            is ApplicationEvent.OnDescriptionChange -> {
                _state.update {
                    it.copy(
                        description = event.state
                    )
                }
            }

            is ApplicationEvent.OnProjectIdChange -> {
                _state.update {
                    it.copy(
                        projectId = event.id
                    )
                }
            }

            is ApplicationEvent.OnPostApplicationRespState -> {
                _state.update {
                    it.copy(
                        applicationRespState = event.state
                    )
                }
            }

            ApplicationEvent.Apply -> {
                postApplication()
            }

            else -> Unit
        }
    }

    private fun postApplication() {
        postApplicationUseCase.invoke(
            token = "Bearer ${_state.value.accessToken}",
            PostApplicationBody(_state.value.projectId, _state.value.description)
        ).onEach { result: Resource<PostApplicationResp> ->
            when (result) {
                is Resource.Success -> {
                    val response: PostApplicationResp? = result.data
                    onEvent(
                        ApplicationEvent.OnPostApplicationRespState(
                            PostApplicationRespState(
                                response = response
                            )
                        )
                    )
                    Log.e(
                        "TAG",
                        "ProjectByCategoriesResponse->\n ${_state.value}"
                    )
                }

                is Resource.Error -> {
                    Log.e("TAG", "AuthorizationResponseError->\n ${result.message}")
                    onEvent(
                        ApplicationEvent.OnPostApplicationRespState(
                            PostApplicationRespState(
                                error = "${result.message}"
                            )
                        )
                    )
                    refreshToken {
                        postApplication()
                    }
                }

                is Resource.Loading -> {
                    onEvent(
                        ApplicationEvent.OnPostApplicationRespState(
                            PostApplicationRespState(
                                isLoading = true
                            )
                        )
                    )
                }

            }
        }.launchIn(viewModelScope)
    }

    private fun refreshToken(refreshFun: () -> Unit) {
        refreshTokenUseCase.invoke(
            refreshTokenBody = RefreshTokenBody(_state.value.refreshToken)
        ).onEach { result: Resource<RefreshTokenResp> ->
            when (result) {
                is Resource.Success -> {
                    val response: RefreshTokenResp? = result.data
                    response?.let {
                        dataStoreManager.updateAccessToken(it.access)
                        delay(100)
                        refreshFun()
                    }
                    Log.e("TAG", "RefreshTokenResponse->\n ${_state.value.accessToken}")
                }

                is Resource.Error -> {
                    Log.e("TAG", "RefreshTokenResponseError->\n ${result.message}")
                }

                is Resource.Loading -> {
                    onEvent(
                        ApplicationEvent.OnPostApplicationRespState(
                            PostApplicationRespState(
                                isLoading = true
                            )
                        )
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}