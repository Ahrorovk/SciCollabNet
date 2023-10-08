package com.ahrorovkspace.codebeyondearth.core.states

import com.ahrorovkspace.codebeyondearth.domain.profileInfo.model.GetProfileInfoResp

data class GetProfileInfoRespState(
    var isLoading: Boolean = false,
    var response: GetProfileInfoResp? = null,
    val error: String = ""
)
