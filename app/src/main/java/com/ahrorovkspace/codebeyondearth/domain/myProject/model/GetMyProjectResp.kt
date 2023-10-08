package com.ahrorovkspace.codebeyondearth.domain.myProject.model

data class GetMyProjectResp(
    val count: Int,
    val next: String,
    val previous: String,
    val results: List<Result>
)