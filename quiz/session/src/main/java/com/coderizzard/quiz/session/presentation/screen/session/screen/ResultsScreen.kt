package com.coderizzard.quiz.session.presentation.screen.session.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.coderizzard.core.data.AsyncData
import com.coderizzard.core.data.model.Quiz
import com.coderizzard.core.data.model.question.IdentificationQuestion
import com.coderizzard.core.data.model.question.MCOption
import com.coderizzard.core.data.model.question.MCQuestion
import com.coderizzard.core.data.model.session.QuizSession
import com.coderizzard.core.data.model.session.SessionResult
import com.coderizzard.core.data.model.session.SessionResultWithUserAnswers
import com.coderizzard.core.data.model.session.answer.IdentificationAnswer
import com.coderizzard.core.data.model.session.answer.MCQuestionAnswer
import com.coderizzard.core.data.toAnnotatedString
import java.time.LocalDateTime
import kotlin.random.Random

@Composable
internal fun ResultsScreen(quizId: String) {
    val viewModel : ResultsScreenViewModel = hiltViewModel(key = quizId)
    val data = viewModel.data
    LaunchedEffect(Unit) {
        viewModel.initialize(quizId)
    }
    Content(
        data = data,
        retry = viewModel::retry
    )
}

@Composable
private fun Content(
    data: AsyncData<SessionResultWithUserAnswers>,
    retry: (String) -> Unit,
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
                        onClick = {retry(session.quizId)},
                    ) {
                        Text("Retry")
                    }
                }

                itemsIndexed(items = data.data.userAnswers) { index,userAnswer ->
                    val question = data.data.session.quiz.questions.find { it.id == userAnswer.questionId }
                        ?: throw Exception("There should be a question here")

                    Card(
                        colors = CardDefaults.cardColors(
                            contentColor = if(userAnswer.isCorrect)
                                MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.error,
                        ),
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth().padding(
                                vertical = 16.dp, horizontal = 12.dp,
                            ),
                            verticalArrangement = Arrangement.spacedBy(
                                8.dp,
                            )
                        ) {

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                val questionType = question.getTypeName()
                                Text(
                                    "${index + 1}. $questionType",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Light,
                                )
                                Text(
                                    "${if(userAnswer.isCorrect) question.point else 0}/${question.point}",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Light,
                                )
                            }
                            HorizontalDivider()
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
                                    Column(
                                        verticalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        q.options.map { opt ->
                                            val isOptionCorrect = q.answer.contains(opt.remoteId)
                                            val userPickedOption = userAnswer.correctAnswerIds.contains(opt.remoteId)
                                            val color = if(isOptionCorrect)
                                                MaterialTheme.colorScheme.primary
                                            else if(userPickedOption)
                                                MaterialTheme.colorScheme.error
                                            else
                                                MaterialTheme.colorScheme.surface
                                            OutlinedCard(
                                                colors = CardDefaults.outlinedCardColors(
                                                    containerColor = color,
                                                ),
                                            ) {
                                                Box(
                                                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                                                ) {
                                                    Text(
                                                        opt.text.toAnnotatedString(),
                                                        modifier = Modifier.fillMaxWidth(),
                                                    )
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
    }
}

@Preview
@Composable
private fun ResultScreenPreview() {
    val questions = List(5) { index ->
        val questionId = "q${index}"
        MCQuestion(
            id =questionId,
            remoteId = questionId,
            imageLink = "",
            localImagePath = "",
            quizId = "quiz1",
            options = List(4) { optIndex->
                val optId = "mco${optIndex}"
                MCOption(
                    questionId = questionId,
                    remoteId = optId,
                    id = optId,
                    text = (optIndex + 1).toString(),
                )
            },
            text = "What is 1+1?",
            answer = listOf("mco1"),
            point = 1,
        )
    }
    val quiz = Quiz(
        allQuestions = questions,
        id = "quiz1",
        name = "Some quiz",
        remoteId = "",
        localImagePath = "",
        author = "",
        createdAt = LocalDateTime.now(),
        imageLink = "",
    )
    val userAnswers = questions.mapIndexed { index, question ->
        val isCorrect = Random.nextBoolean()
        val answer = if(isCorrect)
            question.answer
        else
            listOf("mco${(index + 1) % questions.size}")
        MCQuestionAnswer(
            correctAnswerIds = answer,
            quizId = question.quizId,
            questionId = question.id,
            isCorrect = isCorrect,
        )
    }
    Surface() {
        Content(
            data = AsyncData.Success(
                data = SessionResultWithUserAnswers(
                    session = QuizSession(
                        quiz = quiz,
                        quizId = "quiz1",
                        questionOrder = emptyList(),
                        currentQuestionIndex = 0,
                        startedAt = LocalDateTime.now(),
                    ),
                    sessionResult = SessionResult(
                        id = "session1",
                        quizId = "quiz1",
                        marks = userAnswers.count {it.isCorrect},
                        dateFinished = LocalDateTime.now(),
                        totalPoints = 5,
                    ),
                    userAnswers = userAnswers,
                )
            ),
            retry = {}
        )
    }
}