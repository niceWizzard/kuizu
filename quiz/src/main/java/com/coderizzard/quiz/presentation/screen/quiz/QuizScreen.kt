package com.coderizzard.quiz.presentation.screen.quiz

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.toRoute
import com.coderizzard.core.data.navigation.RootRoute

@Composable
fun QuizScreen(
    navController: NavController
) {
    val quizRoute = navController.currentBackStackEntry?.toRoute<RootRoute.Quiz>()
    Column {
        quizRoute?.id?.let { Text(it) }
    }

}