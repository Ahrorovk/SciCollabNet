package com.ahrorovkspace.codebeyondearth.presentation.myProjectsScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahrorovkspace.codebeyondearth.core.Resource
import com.ahrorovkspace.codebeyondearth.core.states.GetMyProjectRespState
import com.ahrorovkspace.codebeyondearth.data.local.DataStoreManager
import com.ahrorovkspace.codebeyondearth.domain.myProject.model.GetMyProjectResp
import com.ahrorovkspace.codebeyondearth.domain.myProject.useCase.GetMyProjectUseCase
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
class MyProjectViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val getMyProjectUseCase: GetMyProjectUseCase,
    private val refreshTokenUseCase: RefreshTokenUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(MyProjectState())
    val state = _state.stateIn(
        viewModelScope + Dispatchers.IO,
        SharingStarted.WhileSubscribed(5000),
        MyProjectState()
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

    fun onEvent(event: MyProjectEvent) {
        when (event) {
            is MyProjectEvent.OnGetMyProjectRespStateChange -> {
                _state.update {
                    it.copy(
                        myProjectRespState = event.state
                    )
                }
            }

            MyProjectEvent.GetMyProject -> {
                getMyProject()
            }
        }
    }

    private fun getMyProject() {
        getMyProjectUseCase.invoke(_state.value.accessToken)
            .onEach { result: Resource<GetMyProjectResp> ->
                when (result) {
                    is Resource.Success -> {
                        val response: GetMyProjectResp? = result.data
                        onEvent(
                            MyProjectEvent.OnGetMyProjectRespStateChange(
                                GetMyProjectRespState(
                                    response = response
                                )
                            )
                        )
                        Log.e(
                            "TAG",
                            "ProjectByCategoriesResponse->\n ${_state.value.myProjectRespState}"
                        )
                    }

                    is Resource.Error -> {
                        Log.e("TAG", "AuthorizationResponseError->\n ${result.message}")
                        onEvent(
                            MyProjectEvent.OnGetMyProjectRespStateChange(
                                GetMyProjectRespState(
                                    error = "${result.message}"
                                )
                            )
                        )
                        refreshToken {
                            getMyProject()
                        }
                    }

                    is Resource.Loading -> {
                        onEvent(
                            MyProjectEvent.OnGetMyProjectRespStateChange(
                                GetMyProjectRespState(
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
                        MyProjectEvent.OnGetMyProjectRespStateChange(
                            GetMyProjectRespState(
                                isLoading = true
                            )
                        )
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}