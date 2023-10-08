package com.ahrorovkspace.codebeyondearth.presentation.splashScreen

sealed class SplashEvent {
    data class OnStateRegisteredChange(val state: Boolean) : SplashEvent()
    object GoToMainScreen : SplashEvent()
    object GoToAuthScreen : SplashEvent()
}
