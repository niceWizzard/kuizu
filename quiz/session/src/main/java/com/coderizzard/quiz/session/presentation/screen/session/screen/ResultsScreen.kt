package com.coderizzard.quiz.session.presentation.screen.session.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.coderizzard.core.data.AsyncData
import com.coderizzard.core.data.model.Quiz
import com.coderizzard.core.data.model.question.IdentificationQuestion
import com.coderizzard.core.data.model.question.MCQuestion
import com.coderizzard.core.data.model.session.QuizSession
import com.coderizzard.core.data.model.session.SessionResult
import com.coderizzard.core.data.model.session.SessionResultWithUserAnswers
import com.coderizzard.core.data.model.session.answer.IdentificationAnswer
import com.coderizzard.core.data.model.session.answer.MCQuestionAnswer
import com.coderizzard.core.data.toAnnotatedString
import java.time.LocalDateTime

@Composable
internal fun ResultsScreen(quizId: String) {
    val viewModel : ResultsScreenViewModel = hiltViewModel(key = quizId)
    val data = viewModel.data
    LaunchedEffect(Unit) {
        viewModel.initialize(quizId)
    }
    Content(data = data)
}

@Composable
private fun Content(
    data: AsyncData<SessionResultWithUserAnswers>
) {
    LazyColumn (
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 12.dp, horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        when (data) {
            is AsyncData.Error -> TODO()
            AsyncData.Loading -> {
                item {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        CircularProgressIndicator()
                        Text("Calculating results...")
                    }
                }
            }

            is AsyncData.Success -> {
                val session = data.data.session
                item {
                    val sessionResult = data.data.sessionResult
                    Text(
                        session.quiz.name,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Text(
                        """
                        You have answered <b>${sessionResult.marks}/${sessionResult.totalPoints}</b> questions correctly
                    """.trimIndent().toAnnotatedString()
                    )
                    ElevatedButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {},
                    ) {
                        Text("Retry")
                    }
                }

                items(items = data.data.userAnswers) { userAnswer ->
                    val question = data.data.session.quiz.questions.find { it.id == userAnswer.questionId }
                        ?: throw Exception("There should be a question here")
                    Card {
                        Column(
                            modifier = Modifier.fillMaxWidth().padding(
                                vertical = 16.dp, horizontal = 12.dp,
                            ),
                            verticalArrangement = Arrangement.spacedBy(
                                8.dp,
                            )
                        ) {
                            Text(question.text.toAnnotatedString())
                            when(userAnswer) {
                                is IdentificationAnswer -> {
                                    val q = question as IdentificationQuestion
                                    OutlinedTextField(
                                        value = buildString {
                                            append(userAnswer.correctAnswer)
                                            if(userAnswer.correctAnswer.isBlank())
                                                append("empty")
                                        },
                                        onValueChange = {},
                                        enabled = false,
                                        modifier = Modifier.fillMaxWidth(),
                                        colors = TextFieldDefaults.colors(
                                            disabledTextColor = if(userAnswer.isCorrect)
                                                MaterialTheme.colorScheme.primary
                                            else
                                                MaterialTheme.colorScheme.error,

                                        )
                                    )
                                    if(!userAnswer.isCorrect) {
                                        Text(
                                            "Correct: ${q.answer}",
                                            modifier = Modifier.fillMaxWidth(),
                                            color = MaterialTheme.colorScheme.error,
                                        )
                                    }
                                }
                                is MCQuestionAnswer -> {
                                    val q = question as MCQuestion
                                    q.options.map { opt ->
                                        ElevatedButton(
                                            onClick = {},
                                            enabled = false,
                                            modifier = Modifier.fillMaxWidth(),
                                        ) {
                                            Text(buildString {
                                                if(userAnswer.correctAnswerIds.contains(opt.remoteId))
                                                    append("Answer")
                                                append(opt.text.toAnnotatedString())
                                            })
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
private fun ResultScreenPreview() {
    val quiz = Quiz(
        questions = emptyList(),
        id = "1",
        name = "Some quiz",
        remoteId = "",
        localImagePath = "",
        author = "",
        createdAt = LocalDateTime.now(),
        imageLink = "",
    )
    Surface() {
        Content(
            data = AsyncData.Success(
                data = SessionResultWithUserAnswers(
                    session = QuizSession(
                        quiz = quiz,
                        quizId = "1",
                        questionOrder = emptyList(),
                        currentQuestionIndex = 0,
                        startedAt = LocalDateTime.now(),
                    ),
                    sessionResult = SessionResult(
                        id = "",
                        quizId = "",
                        marks = 9,
                        dateFinished = LocalDateTime.now(),
                        totalPoints = 10,
                    ),
                    userAnswers = emptyList(),
                )
            ),
        )
    }
}