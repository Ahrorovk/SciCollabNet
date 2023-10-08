package com.ahrorovkspace.codebeyondearth.core.states

import com.ahrorovkspace.codebeyondearth.domain.main.model.categories.GetCategoryResp

data class GetCategoriesRespState(
    var isLoading: Boolean = false,
    var response: GetCategoryResp? = null,
    val error: String = ""
)
