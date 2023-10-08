package com.ahrorovkspace.codebeyondearth.core.states

import com.ahrorovkspace.codebeyondearth.domain.application.model.PostApplicationResp

data class PostApplicationRespState(
    var isLoading: Boolean = false,
    var response: PostApplicationResp? = null,
    val error: String = ""
)
