package com.coderizzard.quiz.presentation.screen.quiz_list

import android.app.Activity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelStoreOwner
import com.coderizzard.core.data.model.Quiz
import com.coderizzard.core.data.model.question.IdentificationQuestion
import com.coderizzard.core.data.model.question.MCOption
import com.coderizzard.core.data.model.question.MCQuestion
import com.coderizzard.quiz.presentation.screen.quiz_list.add_quiz.AddQuizDialog
import kotlinx.coroutines.delay
import java.time.LocalDateTime
import java.util.UUID

@Composable
fun QuizListScreen(
    onQuizClick: (id: String) -> Unit,
) {
    val activity = LocalContext.current as Activity as ViewModelStoreOwner
    val quizListScreenViewModel: QuizListScreenViewModel = hiltViewModel(activity)
    val quizList by quizListScreenViewModel.allQuizzes.collectAsState()
    val addQuizDialogState by quizListScreenViewModel.addQuizListState.collectAsState()

    val addQuizFieldFocusRequester = remember { FocusRequester() }
    LaunchedEffect(addQuizDialogState) {
        if(addQuizDialogState is AddQuizDialogState.Shown) {
            delay(10)
            addQuizFieldFocusRequester.requestFocus()
        }
    }
    if(addQuizDialogState !is AddQuizDialogState.Hidden) {
        AddQuizDialog(
            onDismissRequest = {
                quizListScreenViewModel.dismissAddQuizDialog()
            },
            addQuizFieldFocusRequester,
        )
    }
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
        modifier = Modifier.fillMaxSize().padding(horizontal = 12.dp)
    ) {
        Text("All Quizzes", fontSize = 32.sp, fontWeight = FontWeight.Bold)
        HorizontalDivider()
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
                            quizId = "asdf",
                            remoteId = "remoteIdlkajsdf"
                        ),
                        MCQuestion(
                            text = "What is 2 + 2?",
                            answer = listOf(
                                "akl;jds"
                            ),
                            options = List(4) {
                                MCOption(
                                    remoteId = UUID.randomUUID().toString(),
                                    id = UUID.randomUUID().toString(),
                                    questionId = UUID.randomUUID().toString(),
                                    text = (it + 1).toString()
                                )
                            },
                            id = "lkajiolqa",
                            quizId = "ljkasfd",
                            point = 1,
                            remoteId = "lkasdf89f"
                        )
                    )
                )
            )
        )
    )
}