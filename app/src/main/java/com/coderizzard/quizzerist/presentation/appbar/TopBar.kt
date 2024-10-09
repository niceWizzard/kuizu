package com.coderizzard.quizzerist.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.coderizzard.quizzerist.presentation.appbar.HomeAppBar
import com.coderizzard.quizzerist.presentation.appbar.RootAppBar
import com.coderizzard.quizzerist.presentation.navigation.HomeRoute
import com.coderizzard.quizzerist.presentation.navigation.RootRoute

@Composable
fun TopBar(navController: NavController) {
    val homeRouteList = HomeRoute.allRoutes
    val rootRouteList = RootRoute.routes

    val backStackEntry by navController.currentBackStackEntryAsState()
    backStackEntry?.let { entry ->
        val routeRes = homeRouteList.find { navRoute ->
            entry.destination.hierarchy.any {
                it.hasRoute(navRoute::class)
            }
        }
        if (routeRes != null) {
            HomeAppBar(routeRes)
        }
        else {
            RootAppBar(
                navController,
                rootRouteList.find { navRoute ->
                    entry.destination.hasRoute(navRoute::class)
                }
            )
        }

    }
}






@Preview
@Composable
private fun PreviewTopBar() {
    TopBar(rememberNavController())
}

