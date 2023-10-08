package com.ahrorovkspace.codebeyondearth.core.states

import com.ahrorovkspace.codebeyondearth.domain.myProject.model.GetMyProjectResp

data class GetMyProjectRespState(
    var isLoading: Boolean = false,
    var response: GetMyProjectResp? = null,
    val error: String = ""
)
