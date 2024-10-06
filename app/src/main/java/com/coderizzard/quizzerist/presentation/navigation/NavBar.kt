package com.coderizzard.quizzerist.presentation.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun NavBar(
    navController: NavController
) {
    val routes = HomeRoute.allRoutes
    var selectedIndex by rememberSaveable { mutableIntStateOf(0) }
    val backStackEntry by navController.currentBackStackEntryAsState()

    backStackEntry?.let {entry ->
        routes.find { route ->
            entry.destination.hierarchy.any{
                it.hasRoute(route::class)
            }
        }?.let {
            NavigationBar {
                routes.forEachIndexed{ index, route ->
                    NavigationBarItem(
                        icon = {
                            Icon(HomeRoute.getImage(route), contentDescription = "App bar button")
                        },
                        label = {Text(route.displayName)},
                        selected = selectedIndex == index,
                        onClick = {
                            selectedIndex = index
                            navController.navigate(route)
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