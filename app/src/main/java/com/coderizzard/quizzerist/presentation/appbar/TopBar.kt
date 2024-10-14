package com.coderizzard.quizzerist.presentation.appbar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.coderizzard.core.data.navigation.HomeRoute
import com.coderizzard.core.data.navigation.RootRoute

@Composable
fun TopBar(navController: NavController) {
    val allRoutes = RootRoute.allRoutes

    val backStackEntry by navController.currentBackStackEntryAsState()
    backStackEntry?.let { entry ->
        allRoutes.find { entry.destination.hasRoute(it::class) }?.let {
            when(it) {
                is RootRoute -> {
                    RootAppBar(
                        navController,
                        it,
                        entry
                    )
                }
                is HomeRoute -> {
                    HomeAppBar(it, navController)
                }
            }
        }
    }
}






@Preview
@Composable
private fun PreviewTopBar() {
    TopBar(rememberNavController())
}

