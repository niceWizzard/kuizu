package com.coderizzard.quiz.session.presentation.screen.session

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.coderizzard.core.data.AsyncData
import com.coderizzard.core.data.model.Quiz
import com.coderizzard.core.data.model.question.IdentificationQuestion
import com.coderizzard.core.data.model.question.MCQuestion
import com.coderizzard.core.data.model.session.QuizSession
import com.coderizzard.core.data.toAnnotatedString
import java.time.LocalDateTime

@Composable
fun SessionScreen(
) {
    val viewModel : SessionScreenViewModel = hiltViewModel()
    val sessionFlow by viewModel.sessionData.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    Content(
        sessionFlow = sessionFlow,
        uiState = uiState,
        onEvent = viewModel::onEvent
    )

}

@Composable
private fun Content(
    sessionFlow: AsyncData<QuizSession>,
    uiState: SessionUiState,
    onEvent : (e : ScreenEvent) -> Unit,
) {
    Surface() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 12.dp, horizontal = 8.dp),
        ) {
            when(sessionFlow) {
                is AsyncData.Error -> TODO()
                AsyncData.Loading -> {
                    CircularProgressIndicator()
                }
                is AsyncData.Success ->  {
                    val session = sessionFlow.data
                    when(uiState){
                        is SessionUiState.Answering -> {
                            val question = uiState.q
                            Card {
                                Column {
                                    Text(question.text.toAnnotatedString())
                                }
                            }
                            when(question) {
                                is IdentificationQuestion -> {
                                    var answer by rememberSaveable { mutableStateOf("") }
                                    TextField(
                                        value = answer,
                                        onValueChange = {answer = it},
                                        modifier = Modifier.fillMaxWidth(),
                                        label = {Text("Answer")}
                                    )
                                    ElevatedButton(
                                        onClick = {
                                            onEvent(ScreenEvent.IdentificationAnswer(answer))
                                        },
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text("Submit")
                                    }
                                }
                                is MCQuestion -> {
                                    Column {
                                        question.options.map { opt ->
                                            ElevatedButton(
                                                onClick = {
                                                    onEvent(ScreenEvent.MCAnswer(listOf(opt.id)))
                                                }
                                            ) {
                                                Text(opt.text.toAnnotatedString())
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        SessionUiState.Finished -> TODO()
                        SessionUiState.Default -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center,
                            ) {
                                Card {
                                    Column(
                                        modifier = Modifier.padding(
                                            12.dp
                                        ).fillMaxWidth(),
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
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun ContentPreview() {
    Content(
        sessionFlow = AsyncData.Success(
            data = QuizSession(
                questionOrder = emptyList(),
                currentQuestionIndex = 0,
                quizId = "i",
                startedAt = LocalDateTime.now(),
                quiz = Quiz(
                    id = "",
                    localImagePath = "",
                    name = "Some quiz",
                    remoteId = "",
                    author = "Smoe author",
                    imageLink = "",
                    createdAt = LocalDateTime.now(),
                    questions = emptyList(),
                )
            )
        ),
        uiState = SessionUiState.Default,
        onEvent = {}
    )
}