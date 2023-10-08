package com.ahrorovkspace.codebeyondearth.presentation.splashScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.plus
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(

) : ViewModel() {
    private val _state = MutableStateFlow(SplashState())
    val state = _state.stateIn(
        viewModelScope + Dispatchers.IO,
        SharingStarted.WhileSubscribed(5000),
        SplashState()
    )

    fun onEvent(event: SplashEvent) {
        when (event) {
            is SplashEvent.OnStateRegisteredChange -> {
                _state.update {
                    it.copy(
                        stateRegistered = event.state
                    )
                }
            }

            else -> Unit
        }
    }
}