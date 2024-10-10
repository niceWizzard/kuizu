package com.coderizzard.quizzerist.presentation.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import com.coderizzard.core.data.navigation.HomeRoute
import com.coderizzard.core.data.navigation.RootRoute
import com.coderizzard.quiz.presentation.screen.add_quiz.AddQuizScreen
import com.coderizzard.quiz.presentation.screen.quiz_list.QuizListScreen

@Composable
fun NavGraph(
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = RootRoute.Home,
        enterTransition = {
            fadeIn(animationSpec = tween(durationMillis = 400))
        },
        exitTransition = {
            fadeOut(animationSpec = tween(durationMillis = 400))
        },
        popEnterTransition = {
            fadeIn(animationSpec = tween(durationMillis = 400))
        },
        popExitTransition = {
            fadeOut(animationSpec = tween(durationMillis = 400))
        }

    ) {
        homeNavGraph(navController)
        composable<RootRoute.QuizSession> {
            val route = navController.currentBackStackEntry?.toRoute<RootRoute.QuizSession>()
            Text("Quiz Session $route")
        }
        composable<RootRoute.QuizAdd> {
            AddQuizScreen(navController)
        }

    }
}

private fun NavGraphBuilder.homeNavGraph(navController: NavHostController) {
    navigation<RootRoute.Home>(
        startDestination = HomeRoute.Quiz
    ) {
        composable<HomeRoute.Quiz>(
        ) {
            QuizListScreen(
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
                            RootRoute.QuizSession(id = "asdfasdfasdf!")
                        )
                    }
                ) {
                    Text("Start Session")
                }
            }
        }
    }
}




