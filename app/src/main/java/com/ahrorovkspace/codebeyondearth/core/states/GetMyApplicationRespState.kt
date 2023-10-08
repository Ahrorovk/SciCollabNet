package com.ahrorovkspace.codebeyondearth.core.states

import com.ahrorovkspace.codebeyondearth.domain.myApplication.model.GetMyApplicationResp

data class GetMyApplicationRespState(
    var isLoading: Boolean = false,
    var response: GetMyApplicationResp? = null,
    val error: String = ""
)
