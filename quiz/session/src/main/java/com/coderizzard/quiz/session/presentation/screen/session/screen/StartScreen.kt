package com.coderizzard.quiz.session.presentation.screen.session.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.coderizzard.core.data.model.session.QuizSession
import com.coderizzard.quiz.session.presentation.screen.session.ScreenEvent

@Composable
internal fun StartScreen(
    session: QuizSession,
    onEvent: (e: ScreenEvent) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Card {
            Column(
                modifier = Modifier
                    .padding(
                        12.dp
                    )
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(session.quiz.name)
                Text(session.quiz.author)
                ElevatedButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        onEvent(ScreenEvent.Start)
                    }
                ) {
                    Text("Start")
                }
            }
        }
    }
}
