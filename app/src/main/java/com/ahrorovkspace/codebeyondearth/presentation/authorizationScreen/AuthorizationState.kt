package com.ahrorovkspace.codebeyondearth.presentation.authorizationScreen

import com.ahrorovkspace.codebeyondearth.core.states.AuthorizationRespState

data class AuthorizationState(
    val username: String = "",
    val password: String = "",
    val authorizationRespState: AuthorizationRespState = AuthorizationRespState()
)
