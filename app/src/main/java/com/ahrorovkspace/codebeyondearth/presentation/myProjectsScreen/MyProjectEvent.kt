package com.ahrorovkspace.codebeyondearth.presentation.myProjectsScreen

import com.ahrorovkspace.codebeyondearth.core.states.GetMyProjectRespState

sealed class MyProjectEvent {
    data class OnGetMyProjectRespStateChange(val state: GetMyProjectRespState) : MyProjectEvent()
    object GetMyProject : MyProjectEvent()
}
