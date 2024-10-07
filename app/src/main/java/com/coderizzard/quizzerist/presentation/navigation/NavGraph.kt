package com.coderizzard.quizzerist.presentation.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.coderizzard.quizzerist.presentation.screens.homescreen.HomeScreen

@Composable
fun NavGraph(
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = RootNav.Home,

    ) {
        homeNavGraph(navController)
        composable<RootNav.QuizSession> {
            val route = navController.currentBackStackEntry?.toRoute<RootNav.QuizSession>()
            Text("Quiz Session $route")
        }

    }
}

private fun NavGraphBuilder.homeNavGraph(navController: NavHostController) {
    navigation<RootNav.Home>(
        startDestination = HomeRoute.Quiz
    ) {
        composable<HomeRoute.Quiz>(
        ) {
            HomeScreen(
                navController = navController,
            )
        }
        composable<HomeRoute.Settings> {
            Text("SEttings ")
        }
        composable<HomeRoute.Sessions> {
            Column {
                Text("SESSIONS")
                ElevatedButton(
                    onClick = {
                        navController.navigate(
                            RootNav.QuizSession(id = "asdfasdfasdf!")
                        )
                    }
                ) {
                    Text("Start Session")
                }
            }
        }
    }
}




