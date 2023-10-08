package com.ahrorovkspace.codebeyondearth.presentation.profileScreen

import com.ahrorovkspace.codebeyondearth.core.states.ChangeProfileInfoRespState
import com.ahrorovkspace.codebeyondearth.core.states.GetProfileInfoRespState

sealed class ProfileEvent {
    data class OnUsernameChange(val state: String) : ProfileEvent()
    data class OnFirstNameChange(val state: String) : ProfileEvent()
    data class OnSecondNameChange(val state: String) : ProfileEvent()
    data class OnEmailChange(val state: String) : ProfileEvent()
    data class OnPhoneChange(val state: String) : ProfileEvent()
    data class OnDescriptionChange(val state: String) : ProfileEvent()
    data class OnGetProfileInfoRespState(val state: GetProfileInfoRespState) : ProfileEvent()
    data class OnChangeProfileInfoRespState(val state: ChangeProfileInfoRespState) : ProfileEvent()
    object SaveChanges : ProfileEvent()
    object GetProfileInfo : ProfileEvent()
}
