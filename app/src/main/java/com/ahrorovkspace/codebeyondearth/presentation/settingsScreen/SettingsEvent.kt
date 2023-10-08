package com.ahrorovkspace.codebeyondearth.presentation.settingsScreen

sealed class SettingsEvent{
    data class OnLanguageStateChange(val language: String) : SettingsEvent()
}
