package com.coderizzard.quizzerist.presentation.action_button

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import com.coderizzard.core.data.navigation.HomeRoute
import com.coderizzard.core.data.navigation.RootRoute

@Composable
fun HomeRouteActionButton(
    route : HomeRoute,
    navigate : (RootRoute) -> Unit
) {
    when(route) {
        is HomeRoute.Quiz -> {
            FloatingActionButton(
                onClick = {navigate(RootRoute.QuizAdd)}
            ) {
                Icon(
                    Icons.Filled.Add,
                    "Add"
                )
            }
        }
        else -> {}
    }
}