package com.ahrorovkspace.codebeyondearth.core.states

import com.ahrorovkspace.codebeyondearth.domain.profileInfo.model.ChangeProfileInfoResp

data class ChangeProfileInfoRespState(
    var isLoading: Boolean = false,
    var response: ChangeProfileInfoResp? = null,
    val error: String = ""
)
