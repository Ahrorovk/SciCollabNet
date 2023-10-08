package com.ahrorovkspace.codebeyondearth.presentation.mainScreen

import com.ahrorovkspace.codebeyondearth.core.states.GetCategoriesRespState
import com.ahrorovkspace.codebeyondearth.core.states.GetProjectByCategoriesRespState
import com.ahrorovkspace.codebeyondearth.domain.main.model.categories.Children

sealed class MainEvent {
    data class OnCategorySelectedChange(val category: List<Children>) : MainEvent()
    data class OnGetCategoriesRespStateChange(val state: GetCategoriesRespState) : MainEvent()
    data class OnGetProjectByCategoriesRespStateChange(val state: GetProjectByCategoriesRespState) :
        MainEvent()
    data class OnSearchProjectChange(val state: String) : MainEvent()
    data class GoToApplication(val id: Int) : MainEvent()
    data class GoToBrieflyDesc(val id: Int) : MainEvent()
    object GoToProfile : MainEvent()
    object GoToMyApplication : MainEvent()
    object GoToAuthorization : MainEvent()
    object GetCategories : MainEvent()
    object GetProjectsByCategories : MainEvent()
    object GoToRegistration : MainEvent()
    object GoToSettings : MainEvent()
    object Logout : MainEvent()
    object Clean : MainEvent()
}
