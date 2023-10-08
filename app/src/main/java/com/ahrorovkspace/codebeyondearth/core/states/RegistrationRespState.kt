package com.ahrorovkspace.codebeyondearth.core.states

import com.ahrorovkspace.codebeyondearth.domain.registration.model.RegistrationResp

data class RegistrationRespState(
    var isLoading: Boolean = false,
    var response: RegistrationResp? = null,
    val error: String = ""
)
