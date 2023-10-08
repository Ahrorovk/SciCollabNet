package com.ahrorovkspace.codebeyondearth.presentation.mainScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahrorovkspace.codebeyondearth.core.Resource
import com.ahrorovkspace.codebeyondearth.core.states.GetCategoriesRespState
import com.ahrorovkspace.codebeyondearth.core.states.GetProjectByCategoriesRespState
import com.ahrorovkspace.codebeyondearth.data.local.DataStoreManager
import com.ahrorovkspace.codebeyondearth.domain.main.model.categories.Children
import com.ahrorovkspace.codebeyondearth.domain.main.model.categories.GetCategoryResp
import com.ahrorovkspace.codebeyondearth.domain.main.model.project.GetProjectByCategoriesBody
import com.ahrorovkspace.codebeyondearth.domain.main.model.project.GetProjectByCategoriesResp
import com.ahrorovkspace.codebeyondearth.domain.main.useCase.GetCategoriesUseCase
import com.ahrorovkspace.codebeyondearth.domain.main.useCase.GetProjectByCategoryUseCase
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
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val refreshTokenUseCase: RefreshTokenUseCase,
    private val getProjectByCategoryUseCase: GetProjectByCategoryUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(MainState())
    val state = _state.stateIn(
        viewModelScope + Dispatchers.IO,
        SharingStarted.WhileSubscribed(5000),
        MainState()
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

    fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.OnSearchProjectChange->{
                _state.update {
                    it.copy(
                        searchState = event.state
                    )
                }
            }
            is MainEvent.OnCategorySelectedChange -> {
                _state.update {
                    it.copy(
                        changedCategories = event.category
                    )
                }
            }

            is MainEvent.GetCategories -> {
                getCategories()
            }

            MainEvent.GetProjectsByCategories -> {
                getProjectByCategories()
            }

            is MainEvent.OnGetProjectByCategoriesRespStateChange -> {
                _state.update {
                    it.copy(
                        projectByCategoriesRespState = event.state
                    )
                }
            }

            is MainEvent.OnGetCategoriesRespStateChange -> {
                event.state.response?.let { resp ->
                    val parentName = mutableListOf<String>()

                    val children = mutableListOf<List<Children>>()

                    resp.results.forEach { it ->
                        parentName.add(it.name)
                        children.add(it.childrens)
                    }

                    _state.update {
                        it.copy(
                            categoriesRespState = event.state,
                            parentCategories = parentName,
                            childrenCategories = children
                        )
                    }
                }
            }
            MainEvent.Clean->{
                viewModelScope.launch {
                    dataStoreManager.updateAccessToken("")
                    dataStoreManager.updateRefreshToken("")
                }
            }
            else -> {}
        }
    }

    private fun getProjectByCategories() {
        val categoriesName = mutableListOf<String>()
        _state.value.changedCategories.forEach {
            categoriesName.add(it.name)
        }
        getProjectByCategoryUseCase.invoke(
            token = "Bearer ${_state.value.accessToken}",
            GetProjectByCategoriesBody(categoriesName)
        ).onEach { result: Resource<GetProjectByCategoriesResp> ->
            when (result) {
                is Resource.Success -> {
                    val response: GetProjectByCategoriesResp? = result.data
                    onEvent(
                        MainEvent.OnGetProjectByCategoriesRespStateChange(
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
                        MainEvent.OnGetProjectByCategoriesRespStateChange(
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
                        MainEvent.OnGetProjectByCategoriesRespStateChange(
                            GetProjectByCategoriesRespState(
                                isLoading = true
                            )
                        )
                    )
                }

            }
        }.launchIn(viewModelScope)
    }

    private fun getCategories() {
        getCategoriesUseCase.invoke("Bearer ${_state.value.accessToken}")
            .onEach { result: Resource<GetCategoryResp> ->
                when (result) {
                    is Resource.Success -> {
                        val response: GetCategoryResp? = result.data
                        onEvent(
                            MainEvent.OnGetCategoriesRespStateChange(
                                GetCategoriesRespState(
                                    response = response
                                )
                            )
                        )
                        Log.e(
                            "TAG",
                            "AuthorizationResponse->\n ${_state.value.categoriesRespState}"
                        )
                    }

                    is Resource.Error -> {
                        Log.e("TAG", "AuthorizationResponseError->\n ${result.message}")
                        onEvent(
                            MainEvent.OnGetCategoriesRespStateChange(
                                GetCategoriesRespState(
                                    error = "${result.message}"
                                )
                            )
                        )
                        refreshToken {
                            getCategories()
                        }
                    }

                    is Resource.Loading -> {
                        onEvent(
                            MainEvent.OnGetCategoriesRespStateChange(
                                GetCategoriesRespState(
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
                        MainEvent.OnGetCategoriesRespStateChange(
                            GetCategoriesRespState(
                                isLoading = true
                            )
                        )
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}