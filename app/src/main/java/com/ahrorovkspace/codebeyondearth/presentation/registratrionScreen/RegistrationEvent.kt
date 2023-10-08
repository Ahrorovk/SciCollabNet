package com.ahrorovkspace.codebeyondearth.presentation.registratrionScreen

import com.ahrorovkspace.codebeyondearth.core.states.RegistrationRespState
import com.ahrorovkspace.codebeyondearth.presentation.authorizationScreen.AuthorizationEvent

sealed class RegistrationEvent{
    data class OnEmailChange(val email: String): RegistrationEvent()
    data class OnUsernameChange(val username: String): RegistrationEvent()
    data class OnPasswordChange(val password: String): RegistrationEvent()
    data class OnPasswordConfirmChange(val passwordConfirm: String): RegistrationEvent()
    data class OnRegistrationRespStateChange(val state: RegistrationRespState): RegistrationEvent()
    object Registration: RegistrationEvent()
    object GoToMainScreen: RegistrationEvent()
    object GoToSignUp : RegistrationEvent()
}
