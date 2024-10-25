package com.coderizzard.kuizu.presentation.action_button

import androidx.compose.runtime.Composable
import com.coderizzard.core.data.navigation.HomeRoute
import com.coderizzard.core.data.navigation.RootRoute
import com.coderizzard.quiz.presentation.screen.quiz_list.QuizListScreenActionButton

@Composable
fun HomeRouteActionButton(
    route : HomeRoute,
    navigate : (RootRoute) -> Unit
) {
    when(route) {
        is HomeRoute.Quiz -> {
            QuizListScreenActionButton()
        }
        else -> {}
    }
}