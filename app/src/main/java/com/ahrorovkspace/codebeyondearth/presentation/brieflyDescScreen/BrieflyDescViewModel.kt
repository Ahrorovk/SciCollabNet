package com.ahrorovkspace.codebeyondearth.presentation.brieflyDescScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahrorovkspace.codebeyondearth.core.Resource
import com.ahrorovkspace.codebeyondearth.core.states.GetProjectByCategoriesRespState
import com.ahrorovkspace.codebeyondearth.data.local.DataStoreManager
import com.ahrorovkspace.codebeyondearth.domain.main.model.project.GetProjectByCategoriesBody
import com.ahrorovkspace.codebeyondearth.domain.main.model.project.GetProjectByCategoriesResp
import com.ahrorovkspace.codebeyondearth.domain.main.useCase.GetProjectByCategoryUseCase
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
class BrieflyDescViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val getProjectByCategoryUseCase: GetProjectByCategoryUseCase,
    private val refreshTokenUseCase: RefreshTokenUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(BrieflyDescState())
    val state = _state.stateIn(
        viewModelScope + Dispatchers.IO,
        SharingStarted.WhileSubscribed(5000),
        BrieflyDescState()
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

    fun onEvent(event: BrieflyDescEvent) {
        when (event) {
            is BrieflyDescEvent.OnGetProjectByCategoriesRespStateChange -> {
                _state.update {
                    it.copy(
                        projectByCategoriesRespState = event.state
                    )
                }
            }

            is BrieflyDescEvent.OnProjectIdChange -> {
                _state.update {
                    it.copy(
                        projectId = event.id
                    )
                }
            }
        }
    }

    private fun getProjectByCategories() {
        getProjectByCategoryUseCase.invoke(
            token = "Bearer ${_state.value.accessToken}",
            GetProjectByCategoriesBody(emptyList())
        ).onEach { result: Resource<GetProjectByCategoriesResp> ->
            when (result) {
                is Resource.Success -> {
                    val response: GetProjectByCategoriesResp? = result.data
                    onEvent(
                        BrieflyDescEvent.OnGetProjectByCategoriesRespStateChange(
                            GetProjectByCategoriesRespState(
                                response = response
                            )
                        )
                    )
                    Log.e(
                        "TAG",
                        "ProjectByCategoriesResponse->\n ${_state.value.projectByCategoriesRespState}"
                    )
                }

                is Resource.Error -> {
                    Log.e("TAG", "AuthorizationResponseError->\n ${result.message}")
                    onEvent(
                        BrieflyDescEvent.OnGetProjectByCategoriesRespStateChange(
                            GetProjectByCategoriesRespState(
                                error = "${result.message}"
                            )
                        )
                    )
                    refreshToken {
                        getProjectByCategories()
                    }
                }

                is Resource.Loading -> {
                    onEvent(
                        BrieflyDescEvent.OnGetProjectByCategoriesRespStateChange(
                            GetProjectByCategoriesRespState(
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
                        BrieflyDescEvent.OnGetProjectByCategoriesRespStateChange(
                            GetProjectByCategoriesRespState(
                                isLoading = true
                            )
                        )
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}