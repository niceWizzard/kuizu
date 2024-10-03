package com.coderizzard.quizzerist.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = NavRoute.HOME
    ) {
        composable(NavRoute.HOME) {
            HomeScreen(navController)
        }
    }
}

sealed class NavRoute {
    companion object {
        const val HOME = "home"
    }
}
