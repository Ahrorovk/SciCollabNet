package com.ahrorovkspace.codebeyondearth.core

sealed class Routes(val route: String) {
    object RegistrationScreen : Routes("RegistrationScreen")
    object SettingsScreen : Routes("SettingsScreen")
    object AuthorizationScreen : Routes("AuthorizationScreen")
    object ApplicationScreen : Routes("ApplicationScreen/{${Constants.PROJECT_ID_ARG}}")
    object MyApplicationScreen : Routes("MyApplicationScreen")
    object MainScreen : Routes("MainScreen")
    object ChatScreen : Routes("ChatScreen")
    object ProfileScreen : Routes("ProfileScreen")
    object BrieflyDescScreen : Routes("BrieflyDescScreen")
    object ChatWithUserScreen : Routes("ChatWithUserScreen/{${Constants.CHAT_ID_ARG}}")
    object SplashScreen : Routes("SplashScreen")
}
