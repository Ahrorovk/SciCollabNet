package com.ahrorovkspace.codebeyondearth.core.states

import com.ahrorovkspace.codebeyondearth.domain.authorization.model.AuthorizationResp

data class AuthorizationRespState(
    var isLoading: Boolean = false,
    var response: AuthorizationResp? = null,
    val error: String = ""
)
