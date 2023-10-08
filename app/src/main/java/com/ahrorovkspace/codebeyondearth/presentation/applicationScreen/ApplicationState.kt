package com.ahrorovkspace.codebeyondearth.presentation.applicationScreen

import com.ahrorovkspace.codebeyondearth.core.states.PostApplicationRespState

data class ApplicationState(
    val description: String= "",
    val projectId: Int = 0,
    val refreshToken: String = "",
    val accessToken: String = "",
    val applicationRespState: PostApplicationRespState = PostApplicationRespState()
)
