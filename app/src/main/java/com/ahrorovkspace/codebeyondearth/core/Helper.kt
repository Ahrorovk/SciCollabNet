package com.ahrorovkspace.codebeyondearth.core

fun getTopBarTitle(currentScreen: String, userId: String = "") = when (currentScreen) {
    Routes.ChatScreen.route -> "Chats"
    Routes.ProfileScreen.route -> "Profile"
    Routes.ChatWithUserScreen.route -> userId
    Routes.SettingsScreen.route -> "Settings"
    Routes.ApplicationScreen.route->"Application"
    Routes.MyApplicationScreen.route->"My Applications"
    Routes.BrieflyDescScreen.route->"About Project"
    else -> ""
}

fun doesScreenHaveTopBar(currentScreen: String) =
    currentScreen!=Routes.SplashScreen.route &&
            currentScreen!=Routes.AuthorizationScreen.route &&
            currentScreen!=Routes.RegistrationScreen.route

fun doesScreenHaveMenu(currentScreen: String) =
    currentScreen!=Routes.SplashScreen.route &&
            currentScreen!=Routes.AuthorizationScreen.route &&
            currentScreen!=Routes.RegistrationScreen.route &&
            currentScreen!=Routes.SettingsScreen.route &&
            currentScreen!=Routes.ProfileScreen.route &&
            currentScreen!=Routes.ChatWithUserScreen.route &&
            currentScreen!=Routes.ApplicationScreen.route &&
            currentScreen!=Routes.MyApplicationScreen.route &&
            currentScreen!=Routes.MyApplicationScreen.route
fun doesScreenHaveBottomBar(currentScreen: String) =
    currentScreen!=Routes.SplashScreen.route &&
            currentScreen!=Routes.AuthorizationScreen.route &&
            currentScreen!=Routes.RegistrationScreen.route &&
            currentScreen!=Routes.SettingsScreen.route &&
            currentScreen!=Routes.ProfileScreen.route &&
            currentScreen!=Routes.ChatWithUserScreen.route &&
            currentScreen!=Routes.ApplicationScreen.route &&
            currentScreen!=Routes.MyApplicationScreen.route &&
            currentScreen!=Routes.MyApplicationScreen.route

