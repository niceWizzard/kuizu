package com.coderizzard.quiz.session.presentation.screen.session

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.coderizzard.core.data.AsyncData
import com.coderizzard.core.data.model.Quiz
import com.coderizzard.core.data.model.question.IdentificationQuestion
import com.coderizzard.core.data.model.question.MCOption
import com.coderizzard.core.data.model.question.MCQuestion
import com.coderizzard.core.data.model.session.QuizSession
import com.coderizzard.quiz.session.presentation.screen.session.screen.AnsweringScreen
import com.coderizzard.quiz.session.presentation.screen.session.screen.ResultsScreen
import com.coderizzard.quiz.session.presentation.screen.session.screen.StartScreen
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
        isQuestionVisible = viewModel.isQuestionVisible
    )

}

@Composable
private fun Content(
    sessionData: AsyncData<QuizSession>,
    uiState: SessionUiState,
    onEvent: (e: ScreenEvent) -> Unit,
    score: Int,
    answeringState: AnsweringState,
    isQuestionVisible: Boolean,
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
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }
                is AsyncData.Success ->  {
                    val session = sessionData.data
                    when(uiState){
                        is SessionUiState.Answering -> {
                            AnimatedVisibility(
                                isQuestionVisible,
                                enter = fadeIn(animationSpec = tween(300)) + slideInHorizontally(
                                    initialOffsetX = { -it },
                                    animationSpec = tween(300)
                                ),
                                exit = fadeOut(animationSpec = tween(300)) + slideOutHorizontally(
                                    targetOffsetX = { it },
                                    animationSpec = tween(300)
                                )
                            ) {
                                AnsweringScreen(
                                    uiState,
                                    answeringState,
                                    session,
                                    score,
                                    onEvent
                                )
                            }
                        }
                        SessionUiState.Finished -> {
                            Row {
                                CircularProgressIndicator()
                                Text("Saving results...")
                            }
                        }
                        SessionUiState.Default -> {
                            StartScreen(session, onEvent)
                        }

                        SessionUiState.Results -> {
                            ResultsScreen(session.quizId)
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
        allQuestions = listOf(question1, question2, question3, question4, question5),
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
        onEvent = {},
        score = 0,
        answeringState = AnsweringState.Correct,
        isQuestionVisible = true,
    )
}