package com.ahrorovkspace.codebeyondearth.presentation.authorizationScreen

import com.ahrorovkspace.codebeyondearth.core.states.AuthorizationRespState

sealed class AuthorizationEvent {
    data class OnLoginChange(val login: String) : AuthorizationEvent()
    data class OnPasswordChange(val password: String) : AuthorizationEvent()
    data class OnAuthorizationStateChange(val state: AuthorizationRespState) : AuthorizationEvent()
    object Authorization : AuthorizationEvent()
    object GoToMainScreen : AuthorizationEvent()
    object GoToSignUp : AuthorizationEvent()
}
