package com.ahrorovkspace.codebeyondearth.app.navigation.components

import androidx.compose.material.BottomNavigation
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.navigation.NavController
import com.ahrorovkspace.codebeyondearth.app.theme.BLUE
import com.ahrorovkspace.codebeyondearth.core.Routes
import com.ahrorovkspace.codebeyondearth.core.models.BottomNavDestination

@Composable
fun CBEBottomBar(navController: NavController) {
    BottomNavigation(
        backgroundColor = White.copy(alpha = 0.95F),
        contentColor = BLUE
    ) {
        bottomNavDestinations.forEach { navItem ->
            BottomNavItem(navController = navController, item = navItem)
        }

    }
}
val bottomNavDestinations = listOf(
    BottomNavDestination(
        label = "Projects",
        destinationRoute = Routes.MainScreen.route,
        icon= Icons.Default.Home
    ),
    BottomNavDestination(
        label = "Chats",
        destinationRoute = Routes.ChatScreen.route,
        icon= Icons.Default.Chat
    )
)