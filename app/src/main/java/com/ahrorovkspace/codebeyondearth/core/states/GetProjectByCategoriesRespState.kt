package com.ahrorovkspace.codebeyondearth.core.states

import com.ahrorovkspace.codebeyondearth.domain.main.model.project.GetProjectByCategoriesResp

data class GetProjectByCategoriesRespState(
    var isLoading: Boolean = false,
    var response: GetProjectByCategoriesResp? = null,
    val error: String = ""
)
