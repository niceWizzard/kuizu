package com.coderizzard.kuizu.presentation.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.coderizzard.core.data.navigation.HomeRoute
import com.coderizzard.core.data.navigation.RootRoute
import com.coderizzard.quiz.presentation.screen.quiz.QuizScreen
import com.coderizzard.quiz.presentation.screen.quiz_list.QuizListScreen
import com.coderizzard.quiz.session.presentation.screen.session.SessionScreen
import com.coderizzard.quiz.session.presentation.screen.sessions.SessionListScreen

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
            SessionScreen()
        }
        composable<RootRoute.Quiz> {
            QuizScreen()
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
                onQuizClick = { id ->
                    navController.navigate(RootRoute.Quiz(id))
                }
            )
        }
        composable<HomeRoute.Settings> {
            Text("SEttings ")
        }
        composable<HomeRoute.Sessions> {
            SessionListScreen()
        }
    }
}




