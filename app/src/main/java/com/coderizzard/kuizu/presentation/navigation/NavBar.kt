package com.coderizzard.kuizu.presentation.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.coderizzard.core.data.navigation.HomeRoute

@Composable
fun NavBar(
    navController: NavController
) {
    val routes = HomeRoute.allRoutes
    val backStackEntry by navController.currentBackStackEntryAsState()

    backStackEntry?.let {entry ->
        routes.find { route ->
            entry.destination.hierarchy.any{
                it.hasRoute(route::class)
            }
        }?.let {
            NavigationBar {
                routes.forEach{  route ->
                    val isActive = route == it
                    NavigationBarItem(
                        icon = {
                            Icon(HomeRoute.getImage(route, isActive), contentDescription = "App bar button")
                        },
                        label = {Text(route.displayName)},
                        selected = isActive,
                        onClick = {
                            if(!isActive) {
                                navController.navigate(route)
                            }
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewNavBar() {
    NavBar(rememberNavController())
}