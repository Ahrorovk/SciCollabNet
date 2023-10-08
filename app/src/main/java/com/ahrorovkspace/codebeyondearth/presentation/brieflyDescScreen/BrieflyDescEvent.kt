package com.ahrorovkspace.codebeyondearth.presentation.brieflyDescScreen

import com.ahrorovkspace.codebeyondearth.core.states.GetProjectByCategoriesRespState
import com.ahrorovkspace.codebeyondearth.presentation.mainScreen.MainEvent

sealed class BrieflyDescEvent{
    data class OnProjectIdChange(val id: Int): BrieflyDescEvent()
    data class OnGetProjectByCategoriesRespStateChange(val state: GetProjectByCategoriesRespState): BrieflyDescEvent()
}
