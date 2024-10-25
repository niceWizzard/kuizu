package com.coderizzard.kuizu.presentation.action_button

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import com.coderizzard.core.data.navigation.HomeRoute
import com.coderizzard.core.data.navigation.RootRoute

@Composable
fun AppActionButton(navController: NavController) {
    val allRoutes = RootRoute.allRoutes

    val backStackEntry by navController.currentBackStackEntryAsState()
    backStackEntry?.let { entry ->
        allRoutes.find { entry.destination.hasRoute(it::class) }?.let {
            when(it) {
                is RootRoute -> {
                    RootRouteActionButton(
                        route = it,
                    )
                }
                is HomeRoute -> {
                    HomeRouteActionButton(
                        route = it,
                        navigate = { route ->
                            navController.navigate(route)
                        }
                    )
                }
            }
        }
    }
}