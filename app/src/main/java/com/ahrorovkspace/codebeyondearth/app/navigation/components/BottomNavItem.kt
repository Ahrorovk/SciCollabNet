package com.ahrorovkspace.codebeyondearth.app.navigation.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ahrorovkspace.codebeyondearth.app.theme.BLUE
import com.ahrorovkspace.codebeyondearth.app.theme.PrimaryText
import com.ahrorovkspace.codebeyondearth.core.Routes
import com.ahrorovkspace.codebeyondearth.core.models.BottomNavDestination

@Composable
fun RowScope.BottomNavItem(
    navController: NavController,
    item: BottomNavDestination
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigationItem(
        selected = currentDestination?.hierarchy?.any {
            when (item.destinationRoute) {
                Routes.MainScreen.route -> {
                    it.route == Routes.MainScreen.route
                }

                Routes.ChatScreen.route -> {
                    it.route == Routes.ChatScreen.route
                }

                else -> {
                    it.route == Routes.MainScreen.route
                }
            }
        } == true,
        onClick = {
            if (currentDestination?.route != item.destinationRoute)
                navigateToScreen(item.destinationRoute, navController)
        },
        icon = {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = item.icon,
                contentDescription = "BottomNavIcon"
            )
        },
        label = {
            Text(
                text = item.label,
                fontSize = 10.sp
            )
        },
        selectedContentColor = BLUE,
        unselectedContentColor = PrimaryText
    )
}

private fun navigateToScreen(route: String, navController: NavController) {
    navController.navigate(route = route) {
        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}