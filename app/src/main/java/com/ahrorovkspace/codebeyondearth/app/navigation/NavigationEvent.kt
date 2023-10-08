package com.ahrorovkspace.codebeyondearth.app.navigation

sealed class NavigationEvent{
    data class OnSearchStateChange(val search: String): NavigationEvent()
}
