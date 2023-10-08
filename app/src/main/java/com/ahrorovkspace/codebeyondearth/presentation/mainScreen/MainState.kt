package com.ahrorovkspace.codebeyondearth.presentation.mainScreen

import com.ahrorovkspace.codebeyondearth.core.states.GetCategoriesRespState
import com.ahrorovkspace.codebeyondearth.core.states.GetProjectByCategoriesRespState
import com.ahrorovkspace.codebeyondearth.domain.main.model.categories.Children

data class MainState(
    val searchState: String = "",
    val parentCategories: List<String> = emptyList(),
    val childrenCategories: List<List<Children>> = emptyList(),
    val changedCategories: List<Children> = emptyList(),
    val categoriesRespState: GetCategoriesRespState = GetCategoriesRespState(),
    val projectByCategoriesRespState: GetProjectByCategoriesRespState = GetProjectByCategoriesRespState(),
    val refreshToken: String ="",
    val accessToken: String =""
)
