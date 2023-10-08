package com.ahrorovkspace.codebeyondearth.presentation.myApplicationScreen

import com.ahrorovkspace.codebeyondearth.core.states.GetMyApplicationRespState

data class MyApplicationState(
    val myApplicationRespState: GetMyApplicationRespState = GetMyApplicationRespState(),
    val refreshToken: String = "",
    val accessToken: String = "",
    val approveApplication: List<Int> = emptyList()
)
