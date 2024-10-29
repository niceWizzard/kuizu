package com.coderizzard.quiz.session.presentation.screen.session.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.coderizzard.core.data.model.question.IdentificationQuestion
import com.coderizzard.core.data.model.question.MCQuestion
import com.coderizzard.core.data.model.session.QuizSession
import com.coderizzard.core.data.toAnnotatedString
import com.coderizzard.core.presentation.expandable_image.ExpandableImage
import com.coderizzard.quiz.session.presentation.screen.session.AnsweringState
import com.coderizzard.quiz.session.presentation.screen.session.ScreenEvent
import com.coderizzard.quiz.session.presentation.screen.session.SessionUiState

@Composable
internal fun AnsweringScreen(
    uiState: SessionUiState.Answering,
    answeringState: AnsweringState,
    session: QuizSession,
    score: Int,
    onEvent: (e: ScreenEvent) -> Unit
) {
    val question = uiState.q
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp, alignment = Alignment.CenterVertically)
    ) {
        Card(
            colors = when (answeringState) {
                AnsweringState.Correct -> {
                    CardDefaults.cardColors(
                        contentColor = MaterialTheme.colorScheme.primary,
                    )
                }

                is AnsweringState.IncorrectIdentificationAnswer,
                is AnsweringState.IncorrectMCAnswer -> {
                    CardDefaults.cardColors(
                        contentColor = MaterialTheme.colorScheme.error,
                    )
                }

                AnsweringState.Unanswered -> CardDefaults.cardColors()
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(
                        min = 128.dp,
                    )
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(
                        12.dp, alignment = Alignment.CenterHorizontally,
                    )
                ) {
                    Text(
                        "Question: ${session.currentQuestionIndex + 1}/${session.questionOrder.size}",
                        fontWeight = FontWeight.Light,
                        textAlign = TextAlign.Center,
                    )
                    Text(
                        "Correct: $score",
                        fontWeight = FontWeight.Light,
                        textAlign = TextAlign.Center,
                    )
                }
                Spacer(
                    modifier = Modifier.height(18.dp)
                )
                if (question.localImagePath.isNotBlank()) {
                    ExpandableImage(
                        modifier = Modifier.fillMaxWidth(),
                        imageUrl = question.localImagePath,
                        contentDescription = "Current question image"
                    )
                }
                Text(
                    question.text.toAnnotatedString(),
                    fontSize = 18.sp,
                    modifier = Modifier.verticalScroll(rememberScrollState())

                )
            }
        }
        when (question) {
            is IdentificationQuestion -> {
                ComposableIdentificationQuestion(
                    onEvent,
                    answeringState,
                    question
                )
            }

            is MCQuestion -> {
                ComposableMCQuestion(question, onEvent, answeringState)
            }
        }
    }
}

@Composable
private fun ComposableMCQuestion(
    question: MCQuestion,
    onEvent: (e: ScreenEvent) -> Unit,
    answeringState: AnsweringState
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        question.options.map { opt ->
            ElevatedButton(
                onClick = {
                    onEvent(ScreenEvent.MCAnswer(listOf(opt.remoteId)))
                },
                modifier = Modifier.fillMaxWidth(),
                colors = when (answeringState) {
                    AnsweringState.Correct -> {
                        if (question.answer.contains(opt.remoteId))
                            ButtonDefaults.buttonColors(
                                disabledContainerColor = MaterialTheme.colorScheme.secondary,
                                disabledContentColor = MaterialTheme.colorScheme.onSecondary,
                            )
                        else
                            ButtonDefaults.buttonColors()
                    }

                    is AnsweringState.IncorrectMCAnswer -> {
                        if (answeringState.answers.contains(opt.remoteId)) {
                            ButtonDefaults.buttonColors(
                                disabledContainerColor = Color.Red,
                                disabledContentColor = Color.Black,
                            )
                        } else if (question.answer.contains(opt.remoteId))
                            ButtonDefaults.buttonColors(
                                disabledContainerColor = MaterialTheme.colorScheme.primary,
                                disabledContentColor = MaterialTheme.colorScheme.onPrimary,
                            )
                        else
                            ButtonDefaults.buttonColors()
                    }

                    is AnsweringState.IncorrectIdentificationAnswer,
                    AnsweringState.Unanswered -> ButtonDefaults.buttonColors()
                },
                enabled = answeringState == AnsweringState.Unanswered
            ) {
                Text(
                    opt.text.toAnnotatedString(),
                    modifier = Modifier.padding(16.dp),
                    fontSize = 16.sp,
                )
            }
        }
    }
}

@Composable
private fun ComposableIdentificationQuestion(
    onEvent: (e: ScreenEvent) -> Unit,
    answeringState: AnsweringState,
    question: IdentificationQuestion
) {
    var userAnswer by rememberSaveable { mutableStateOf("") }
    LaunchedEffect(question.id) {
        userAnswer = ""
    }
    TextField(
        value = userAnswer,
        onValueChange = {  userAnswer = it  },
        modifier = Modifier.fillMaxWidth(),
        label = { Text("Answer") },
        singleLine = true,
        enabled = answeringState == AnsweringState.Unanswered,
    )
    ElevatedButton(
        onClick = {
            onEvent(ScreenEvent.IdentificationAnswer(userAnswer))
        },
        modifier = Modifier.fillMaxWidth(),
        enabled = answeringState == AnsweringState.Unanswered,
    ) {
        Text("Submit")
    }
    when (answeringState) {
        AnsweringState.Correct -> {
            Text(
                "Correct",
                color = MaterialTheme.colorScheme.secondary
            )
        }

        is AnsweringState.IncorrectIdentificationAnswer -> {
            Text(
                "Correct: ${question.answer}",
                color = MaterialTheme.colorScheme.error,
            )
        }

        is AnsweringState.IncorrectMCAnswer,
        AnsweringState.Unanswered -> {
        }
    }
}