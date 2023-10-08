package com.ahrorovkspace.codebeyondearth.presentation.applicationScreen

import com.ahrorovkspace.codebeyondearth.core.states.PostApplicationRespState

sealed class ApplicationEvent{
    data class OnDescriptionChange(val state: String): ApplicationEvent()
    data class OnProjectIdChange(val id: Int): ApplicationEvent()
    data class OnPostApplicationRespState(val state: PostApplicationRespState): ApplicationEvent()
    object Apply: ApplicationEvent()
    object GoToMain: ApplicationEvent()
}
