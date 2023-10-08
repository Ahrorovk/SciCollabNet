package com.ahrorovkspace.codebeyondearth.domain.myApplication.model

data class GetMyApplicationResp(
    val count: Int,
    val next: String,
    val previous: String,
    val results: List<Result>
)