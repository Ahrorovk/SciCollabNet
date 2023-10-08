package com.ahrorovkspace.codebeyondearth.presentation.profileScreen

import com.ahrorovkspace.codebeyondearth.core.states.ChangeProfileInfoRespState
import com.ahrorovkspace.codebeyondearth.core.states.GetProfileInfoRespState

data class ProfileState(
    val username: String = "",
    val email: String = "",
    val phone: String = "",
    val firstName: String = "",
    val secondName: String = "",
    val categories:List<Int> = emptyList(),
    val description: String = "",
    val refreshToken: String = "",
    val accessToken: String = "",
    val imageUrl:String = "",
    val getProfileInfoRespState: GetProfileInfoRespState = GetProfileInfoRespState(),
    val changeProfileInfoRespState: ChangeProfileInfoRespState = ChangeProfileInfoRespState()
)