package com.coderizzard.quiz.presentation.screen.quiz_list

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.coderizzard.core.data.navigation.RootRoute

@Composable
fun QuizListScreenActionButton(
) {
    val viewModel : QuizListScreenViewModel = hiltViewModel()
    FloatingActionButton(
        onClick = {
            viewModel.showAddQuizDialog()
        }
    ) {
        Icon(
            Icons.Filled.Add,
            "Add"
        )
    }
}