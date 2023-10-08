package com.ahrorovkspace.codebeyondearth.presentation.brieflyDescScreen

import com.ahrorovkspace.codebeyondearth.core.states.GetProjectByCategoriesRespState

data class BrieflyDescState(
    val projectId: Int = 0,
    val refreshToken: String = "",
    val accessToken: String = "",
    val projectByCategoriesRespState: GetProjectByCategoriesRespState = GetProjectByCategoriesRespState(),
)
