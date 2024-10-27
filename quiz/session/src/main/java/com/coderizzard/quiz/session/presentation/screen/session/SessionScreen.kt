package com.coderizzard.quiz.session.presentation.screen.session

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.coderizzard.core.data.AsyncData
import com.coderizzard.core.data.model.session.QuizSession

@Composable
fun SessionScreen(
) {
    val viewModel : SessionScreenViewModel = hiltViewModel()
    val sessionFlow by viewModel.sessionFlow.collectAsState(AsyncData.Loading)
    Content(
        sessionFlow = sessionFlow
    )

}

@Composable
private fun Content(
    sessionFlow: AsyncData<QuizSession>
) {
    Surface() {
        Column(
            modifier = Modifier.fillMaxSize().padding(vertical = 12.dp, horizontal = 8.dp),
        ) {
            when(sessionFlow) {
                is AsyncData.Error -> TODO()
                AsyncData.Loading -> {
                    CircularProgressIndicator()
                }
                is AsyncData.Success ->  {
                    val session = sessionFlow.data
                    Text(session.toString())
                }
            }
        }
    }
}

