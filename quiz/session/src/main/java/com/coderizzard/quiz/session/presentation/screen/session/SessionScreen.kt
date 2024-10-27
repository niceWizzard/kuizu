package com.coderizzard.quiz.session.presentation.screen.session

import android.graphics.Paint.Align
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
    val toastMessage = viewModel.toastMessage
    val context = LocalContext.current
    var currentToast by remember {mutableStateOf<Toast?>(null)}
    LaunchedEffect  (toastMessage) {
        if(toastMessage.isNotBlank()) {
            currentToast?.cancel()
            currentToast = Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT)
            currentToast?.show()
        }
    }
    Content(
        sessionData = viewModel.sessionData,
        uiState = uiState,
        onEvent = viewModel::onEvent
    )

}

@Composable
private fun Content(
    sessionData: AsyncData<QuizSession>,
    uiState: SessionUiState,
    onEvent : (e : ScreenEvent) -> Unit,
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
                            Card {
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
                                    Text(
                                        "${session.currentQuestionIndex + 1}/${session.questionOrder.size}",
                                        modifier = Modifier.fillMaxWidth(),
                                        fontWeight = FontWeight.Light,
                                        textAlign = TextAlign.Center,
                                    )
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
                                    var answer by rememberSaveable { mutableStateOf("") }
                                    TextField(
                                        value = answer,
                                        onValueChange = {answer = it},
                                        modifier = Modifier.fillMaxWidth(),
                                        label = {Text("Answer")},
                                        singleLine = true,
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
                                    Column(
                                        verticalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        question.options.shuffled().map { opt ->
                                            ElevatedButton(
                                                onClick = {
                                                    onEvent(ScreenEvent.MCAnswer(listOf(opt.remoteId)))
                                                },
                                                modifier = Modifier.fillMaxWidth()
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
        MCOption(id = "1", text = "Option A", questionId = "q1", remoteId = ""),
        MCOption(id = "2", text = "Option B",  questionId = "q1", remoteId = ""),
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
        answer = listOf("1")
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
        uiState = SessionUiState.Answering(question3),
        onEvent = {}
    )
}