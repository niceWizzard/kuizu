package com.coderizzard.quizzerist.presentation

import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.coderizzard.quizzerist.presentation.navigation.NavRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavController) {
    val routeList = NavRoute.allRoutes

    val backStackEntry by navController.currentBackStackEntryAsState()
    backStackEntry?.let { entry ->
        val routeName  = routeList.find { navRoute ->
            entry.destination.hierarchy.any {
                it.hasRoute(navRoute::class)
            }
        }?.displayName ?: "Cannot find route name"
        TopAppBar(
            title = { Text(routeName)},
        )
    }
}


@Preview
@Composable
private fun PreviewTopBar() {
    TopBar(rememberNavController())
}

