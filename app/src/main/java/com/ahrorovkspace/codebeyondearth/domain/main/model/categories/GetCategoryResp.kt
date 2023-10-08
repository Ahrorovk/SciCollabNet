package com.ahrorovkspace.codebeyondearth.domain.main.model.categories

data class GetCategoryResp(
    val count: Int,
    val next: Any,
    val previous: Any,
    val results: List<Result>
)