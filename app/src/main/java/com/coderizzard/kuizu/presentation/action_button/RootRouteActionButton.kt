package com.coderizzard.kuizu.presentation.action_button

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavBackStackEntry
import com.coderizzard.core.data.navigation.RootRoute
import com.coderizzard.quiz.presentation.screen.quiz.QuizScreenActionButton

@Composable
fun RootRouteActionButton(
    route: RootRoute,
    entry: NavBackStackEntry,
) {
    val context = LocalContext.current
    when(route) {
        is RootRoute.Quiz -> {
            QuizScreenActionButton(entry)
        }
        else -> {}
    }
}