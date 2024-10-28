package com.coderizzard.quiz.session.presentation.screen.session

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
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
import com.coderizzard.core.data.toAnnotatedString
import com.coderizzard.core.presentation.expandable_image.ExpandableImage
import java.time.LocalDateTime

@Composable
fun SessionScreen(
) {
    val viewModel : SessionScreenViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    Content(
        sessionData = viewModel.sessionData,
        uiState = uiState,
        onEvent = viewModel::onEvent,
        score = viewModel.currentScore,
        answeringState = viewModel.answeringState,
        identificationFieldData = viewModel.identificationFieldData
    )

}

@Composable
private fun Content(
    sessionData: AsyncData<QuizSession>,
    uiState: SessionUiState,
    onEvent: (e: ScreenEvent) -> Unit,
    score: Int,
    answeringState: AnsweringState,
    identificationFieldData: String,
) {
    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 12.dp, horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(
                12.dp, alignment = Alignment.CenterVertically,
            ),
        ) {
            when(sessionData) {
                is AsyncData.Error -> TODO()
                AsyncData.Loading -> {
                    CircularProgressIndicator()
                }
                is AsyncData.Success ->  {
                    val session = sessionData.data
                    when(uiState){
                        is SessionUiState.Answering -> {
                            val question = uiState.q
                            Card(
                                colors = when(answeringState) {
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
                                        .padding(24.dp)
                                        .verticalScroll(rememberScrollState()),
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
                                    if(question.localImagePath.isNotBlank()){
                                        ExpandableImage(
                                            modifier = Modifier.fillMaxWidth(),
                                            imageUrl = question.localImagePath,
                                            contentDescription = "Current question image"
                                        )
                                    }
                                    Text(
                                        question.text.toAnnotatedString(),
                                        fontSize = 18.sp,

                                    )
                                }
                            }
                            when(question) {
                                is IdentificationQuestion -> {
                                    TextField(
                                        value = identificationFieldData,
                                        onValueChange = {
                                            onEvent(ScreenEvent.IdentificationFieldUpdate(it))
                                        },
                                        modifier = Modifier.fillMaxWidth(),
                                        label = {Text("Answer")},
                                        singleLine = true,
                                        enabled = answeringState == AnsweringState.Unanswered,
                                    )
                                    ElevatedButton(
                                        onClick = {
                                            onEvent(ScreenEvent.IdentificationAnswer(identificationFieldData))
                                        },
                                        modifier = Modifier.fillMaxWidth(),
                                        enabled = answeringState == AnsweringState.Unanswered,
                                    ) {
                                        Text("Submit")
                                    }
                                    when(answeringState) {
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
                                        AnsweringState.Unanswered -> {}
                                    }
                                }
                                is MCQuestion -> {
                                    Column(
                                        verticalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        question.options.map { opt ->
                                            ElevatedButton(
                                                onClick = {
                                                    onEvent(ScreenEvent.MCAnswer(listOf(opt.remoteId)))
                                                },
                                                modifier = Modifier.fillMaxWidth(),
                                                colors = when(answeringState) {
                                                    AnsweringState.Correct -> {
                                                        if(question.answer.contains(opt.remoteId))
                                                            ButtonDefaults.buttonColors(
                                                                disabledContainerColor = MaterialTheme.colorScheme.secondary,
                                                                disabledContentColor = MaterialTheme.colorScheme.onSecondary,
                                                            )
                                                        else
                                                            ButtonDefaults.buttonColors()
                                                    }
                                                    is AnsweringState.IncorrectMCAnswer -> {
                                                        if(answeringState.answers.contains(opt.remoteId)) {
                                                            ButtonDefaults.buttonColors(
                                                                disabledContainerColor = Color.Red,
                                                                disabledContentColor = Color.Black,
                                                            )
                                                        }
                                                        else if(question.answer.contains(opt.remoteId))
                                                            ButtonDefaults.buttonColors(
                                                                disabledContainerColor =  MaterialTheme.colorScheme.primary,
                                                                disabledContentColor = MaterialTheme.colorScheme.onPrimary,
                                                            )
                                                        else
                                                            ButtonDefaults.buttonColors()
                                                    }
                                                    is AnsweringState.IncorrectIdentificationAnswer,
                                                    AnsweringState.Unanswered ->ButtonDefaults.buttonColors()
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
                            }
                        }
                        SessionUiState.Finished -> {
                            Text("Finished!")
                        }
                        SessionUiState.Default -> {
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
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun ContentPreview() {
    val mcOptions1 = listOf(
        MCOption(id = "1", text = "Option A", questionId = "q1", remoteId = "t"),
        MCOption(id = "2", text = "Option B",  questionId = "q1", remoteId = "q2"),
        MCOption(id = "3", text = "Option C",  questionId = "q1", remoteId = ""),
        MCOption(id = "4", text = "Option D",  questionId = "q1", remoteId = "")
    )

    // Create questions
    val question1 = MCQuestion(
        id = "q1",
        remoteId = "r1",
        text = "What is the capital of France?",
        point = 10,
        quizId = "quiz1",
        options = mcOptions1,
        answer = listOf("q2")
    )

    val question2 = MCQuestion(
        id = "q2",
        remoteId = "r2",
        text = "What is the square root of 16?",
        point = 10,
        quizId = "quiz1",
        options = mcOptions1,
        answer = listOf("2")
    )

    val question3 = IdentificationQuestion(
        id = "q3",
        text = "Identify the symbol for water.",
        point = 10,
        quizId = "quiz1",
        remoteId = "r3",
        answer = "H2O"
    )

    val question4 = MCQuestion(
        id = "q4",
        remoteId = "r4",
        text = "Which planet is known as the Red Planet?",
        point = 10,
        quizId = "quiz1",
        options = mcOptions1,
        answer = listOf("3")
    )

    val question5 = IdentificationQuestion(
        id = "q5",
        text = "Identify the largest mammal on earth.",
        point = 10,
        quizId = "quiz1",
        remoteId = "r5",
        answer = "Blue Whale"
    )

    // Create quiz with questions
    val quiz = Quiz(
        id = "quiz1",
        name = "General Knowledge Quiz",
        author = "QuizMaster",
        createdAt = LocalDateTime.now(),
        questions = listOf(question1, question2, question3, question4, question5),
        remoteId = "remoteQuiz1"
    )

    // Create question order based on question IDs
    val questionOrder = quiz.questions.map { it.id }

    // Create QuizSession
    val session = QuizSession(
        startedAt = LocalDateTime.now(),
        quizId = quiz.id,
        quiz = quiz,
        questionOrder = questionOrder,
        currentQuestionIndex = 0
    )
    Content(
        sessionData = AsyncData.Success(
            data = session
        ),
        uiState = SessionUiState.Answering(question1),
        onEvent = {},
        score = 0,
        answeringState = AnsweringState.Correct,
        identificationFieldData = ""
    )
}