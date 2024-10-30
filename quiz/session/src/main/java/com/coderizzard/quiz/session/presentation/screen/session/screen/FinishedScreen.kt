package com.coderizzard.quiz.session.presentation.screen.session.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.coderizzard.core.data.AsyncData

@Composable
internal fun FinishedScreen(quizId: String) {
    val viewModel : FinishedScreenViewModel = hiltViewModel(key = quizId)
    val sessionData = viewModel.sessionData
    LaunchedEffect(Unit) {
        viewModel.initialize(quizId)
    }
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        when(sessionData) {
            is AsyncData.Error -> TODO()
            AsyncData.Loading -> {
                Row (
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    CircularProgressIndicator()
                    Text("Calculating results...")
                }
            }
            is AsyncData.Success -> {
                val session = sessionData.data
                Text(session.quiz.name)
                Text("You have answered ${session.questionOrder.size} questions")
                ElevatedButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {},
                ) {
                    Text("Retry")
                }
            }
        }
    }
}