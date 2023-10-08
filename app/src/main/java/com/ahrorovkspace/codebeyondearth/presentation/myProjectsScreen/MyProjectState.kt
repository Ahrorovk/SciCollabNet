package com.ahrorovkspace.codebeyondearth.presentation.myProjectsScreen

import com.ahrorovkspace.codebeyondearth.core.states.GetMyProjectRespState

data class MyProjectState(
    val projectName: String = "",
    val refreshToken: String = "",
    val accessToken: String = "",
    val myProjectRespState: GetMyProjectRespState = GetMyProjectRespState()
)
