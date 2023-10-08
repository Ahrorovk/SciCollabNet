package com.ahrorovkspace.codebeyondearth.presentation.profileScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahrorovkspace.codebeyondearth.core.Resource
import com.ahrorovkspace.codebeyondearth.core.states.ChangeProfileInfoRespState
import com.ahrorovkspace.codebeyondearth.core.states.GetProfileInfoRespState
import com.ahrorovkspace.codebeyondearth.data.local.DataStoreManager
import com.ahrorovkspace.codebeyondearth.domain.profileInfo.model.ChangeProfileInfoBody
import com.ahrorovkspace.codebeyondearth.domain.profileInfo.model.ChangeProfileInfoResp
import com.ahrorovkspace.codebeyondearth.domain.profileInfo.model.GetProfileInfoResp
import com.ahrorovkspace.codebeyondearth.domain.profileInfo.useCase.ChangeProfileInfoUseCase
import com.ahrorovkspace.codebeyondearth.domain.profileInfo.useCase.GetProfileInfoUseCase
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
class ProfileViewModel @Inject constructor(
    private val getProfileInfoUseCase: GetProfileInfoUseCase,
    private val dataStoreManager: DataStoreManager,
    private val refreshTokenUseCase: RefreshTokenUseCase,
    private val changeProfileInfoUseCase: ChangeProfileInfoUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(ProfileState())
    val state = _state.stateIn(
        viewModelScope + Dispatchers.IO,
        SharingStarted.WhileSubscribed(5000),
        ProfileState()
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

    fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.OnEmailChange -> {
                _state.update {
                    it.copy(
                        email = event.state
                    )
                }
            }

            is ProfileEvent.OnFirstNameChange -> {
                _state.update {
                    it.copy(
                        firstName = event.state
                    )
                }
            }

            is ProfileEvent.OnPhoneChange -> {
                _state.update {
                    it.copy(
                        phone = event.state
                    )
                }
            }

            is ProfileEvent.OnUsernameChange -> {
                _state.update {
                    it.copy(
                        username = event.state
                    )
                }
            }

            is ProfileEvent.OnGetProfileInfoRespState -> {
                _state.update {
                    it.copy(
                        getProfileInfoRespState = event.state,
                        firstName = event.state.response?.first_name ?: "",
                        secondName = event.state.response?.last_name ?: "",
                        imageUrl = event.state.response?.avatar ?: "",
                        categories = event.state.response?.categories ?: emptyList(),
                        description = event.state.response?.description ?: "",
                        email = event.state.response?.user?.email ?: "",
                        phone = event.state.response?.user?.phone ?: "",
                        username = event.state.response?.user?.username ?: ""
                    )
                }
            }

            is ProfileEvent.OnDescriptionChange -> {
                _state.update {
                    it.copy(
                        description = event.state
                    )
                }
            }

            is ProfileEvent.OnSecondNameChange -> {
                _state.update {
                    it.copy(
                        secondName = event.state
                    )
                }
            }

            ProfileEvent.SaveChanges -> {
                changeProfileInfo()
            }

            ProfileEvent.GetProfileInfo -> {
                getProfileInfo()
            }

            is ProfileEvent.OnChangeProfileInfoRespState -> {
                _state.update {
                    it.copy(
                        changeProfileInfoRespState = event.state
                    )
                }
            }
        }
    }

    private fun getProfileInfo() {
        getProfileInfoUseCase.invoke(
            token = "Bearer ${_state.value.accessToken}"
        ).onEach { result: Resource<GetProfileInfoResp> ->
            when (result) {
                is Resource.Success -> {
                    val response: GetProfileInfoResp? = result.data
                    onEvent(
                        ProfileEvent.OnGetProfileInfoRespState(
                            GetProfileInfoRespState(
                                response = response
                            )
                        )
                    )
                    Log.e(
                        "TAG",
                        "AuthorizationResponse->\n ${_state.value.getProfileInfoRespState}"
                    )
                }

                is Resource.Error -> {
                    Log.e("TAG", "AuthorizationResponseError->\n ${result.message}")
                    refreshToken { getProfileInfo() }
                    onEvent(
                        ProfileEvent.OnGetProfileInfoRespState(
                            GetProfileInfoRespState(
                                error = "${result.message}"
                            )
                        )
                    )
                }

                is Resource.Loading -> {
                    onEvent(
                        ProfileEvent.OnGetProfileInfoRespState(
                            GetProfileInfoRespState(

                                isLoading = true
                            )
                        )
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun changeProfileInfo() {
        changeProfileInfoUseCase.invoke(
            token = "Bearer ${_state.value.accessToken}",
            ChangeProfileInfoBody(
                first_name = _state.value.firstName,
                last_name = _state.value.secondName,
                description = _state.value.description,
                categories = emptyList(),
                phone = _state.value.phone
            )
        ).onEach { result: Resource<ChangeProfileInfoResp> ->
            when (result) {
                is Resource.Success -> {
                    val response: ChangeProfileInfoResp? = result.data
                    onEvent(
                        ProfileEvent.OnChangeProfileInfoRespState(
                            ChangeProfileInfoRespState(
                                response = response
                            )
                        )
                    )
                    getProfileInfo()
                    Log.e(
                        "TAG",
                        "ChangeProfileInfoResponse->\n ${_state.value.changeProfileInfoRespState}"
                    )
                }

                is Resource.Error -> {
                    Log.e("TAG", "ChangeProfileInfoResponseError->\n ${result.message}")
                    refreshToken {
                        changeProfileInfo()
                    }
                    onEvent(
                        ProfileEvent.OnChangeProfileInfoRespState(
                            ChangeProfileInfoRespState(
                                error = "${result.message}"
                            )
                        )
                    )
                }

                is Resource.Loading -> {
                    onEvent(
                        ProfileEvent.OnChangeProfileInfoRespState(
                            ChangeProfileInfoRespState(
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
                        ProfileEvent.OnGetProfileInfoRespState(
                            GetProfileInfoRespState(
                                isLoading = true
                            )
                        )
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}