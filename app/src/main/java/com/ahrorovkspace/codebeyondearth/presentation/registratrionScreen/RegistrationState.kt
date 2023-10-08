package com.ahrorovkspace.codebeyondearth.presentation.registratrionScreen

import com.ahrorovkspace.codebeyondearth.core.states.RegistrationRespState

data class RegistrationState(
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val passwordConfirm: String = "",
    val registrationRespState: RegistrationRespState = RegistrationRespState()
)
