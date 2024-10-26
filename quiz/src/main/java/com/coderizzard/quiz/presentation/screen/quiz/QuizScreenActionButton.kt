package com.coderizzard.quiz.presentation.screen.quiz

import android.widget.Toast
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import com.coderizzard.core.data.navigation.RootRoute

@Composable
fun QuizScreenActionButton(entry : NavBackStackEntry) {
    val viewModel : QuizScreenViewModel = hiltViewModel(entry)
    FloatingActionButton(
        onClick = {
            viewModel.navigationManager.navigateTo(
                RootRoute.CreateSession(
                    viewModel.routeParams().id
                )
            )
        }
    ) {
        Icon(
            Icons.Default.PlayArrow,
            "Start quiz"
        )
    }
}