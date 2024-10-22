package com.coderizzard.quiz.presentation.screen.quiz_list

import android.app.Activity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelStoreOwner
import com.coderizzard.core.data.model.Quiz
import com.coderizzard.core.data.model.question.IdentificationQuestion
import com.coderizzard.core.data.model.question.MCQuestion
import java.time.LocalDateTime

@Composable
fun QuizListScreen(
    onQuizClick: (id: String) -> Unit,
) {
    val activity = LocalContext.current as Activity as ViewModelStoreOwner
    val quizListScreenViewModel: QuizListScreenViewModel = hiltViewModel(activity)
    val quizList by quizListScreenViewModel.allQuizzes.collectAsState()
    QuizListScreenContent(
        quizList,
        onQuizClick = onQuizClick
    )
}

@Composable
private fun QuizListScreenContent(
    quizList: QuizListState,
    onQuizClick: (id : String) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(12.dp)
    ) {
        Text("All Quizzes", fontSize = 32.sp, fontWeight = FontWeight.Bold)
        HorizontalDivider(
            modifier = Modifier.height(32.dp)
        )
        QuizList(quizList, onQuizClick)
    }
}



@Preview
@Composable
private fun QuizListScreenPreview() {
    QuizListScreenContent(
        onQuizClick = {},
        quizList = QuizListState.Success(
            listOf(
                Quiz(
                    id = "lakjfsd",
                    name = "Example Quiz",
                    author = "John Doe",
                    createdAt = LocalDateTime.now(),
                    remoteId = "",
                    questions = listOf(
                        IdentificationQuestion(
                            answer = "2",
                            text = "What is 1 + 1?",
                            point = 1,
                            id = "klasjdfklafsdj",
                            quizId = "asdf"
                        ),
                        MCQuestion(
                            text = "What is 2 + 2?",
                            answer = listOf(0),
                            options = listOf(
                                "1", "2", "3", "4"
                            ),
                            id = "lkajiolqa",
                            quizId = "ljkasfd",
                            point = 1
                        )
                    )
                )
            )
        )
    )
}