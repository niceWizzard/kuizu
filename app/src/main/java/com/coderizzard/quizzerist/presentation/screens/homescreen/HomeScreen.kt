package com.coderizzard.quizzerist.presentation.screens.homescreen

import android.app.Activity
import android.text.style.UnderlineSpan
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
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
import androidx.navigation.NavController
import com.coderizzard.core.data.model.Quiz
import com.coderizzard.core.data.model.question.IdentificationQuestion
import com.coderizzard.core.data.model.question.MultipleChoiceQuestion
import com.coderizzard.core.data.model.question.Question
import java.time.LocalDateTime

@Composable
fun HomeScreen(
    navController: NavController,
) {
    val activity = LocalContext.current as Activity as ViewModelStoreOwner
    val homeScreenViewModel: HomeScreenViewModel = hiltViewModel(activity)
    val quizList by homeScreenViewModel.allQuizzes.collectAsState(emptyList())
    HomeScreenContent(
        quizList
    )
}

@Composable
private fun HomeScreenContent(quizList: List<Quiz>) {
    Column(
        modifier = Modifier.fillMaxSize().padding(12.dp)
    ) {
        Text("All Quizzes", fontSize = 32.sp, fontWeight = FontWeight.Bold)
        HorizontalDivider(
            modifier = Modifier.height(32.dp)
        )
        QuizList(quizList)
    }
}



@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreenContent(quizList = listOf(
        Quiz(
            id = "lakjfsd",
            name = "Example Quiz",
            author = "John Doe",
            createdAt = LocalDateTime.now(),
            questions = listOf(
                IdentificationQuestion(
                    answer = "2",
                    text = "What is 1 + 1?",
                    point = 1,
                    id = "klasjdfklafsdj",
                    quizId = "asdf"
                ),
                MultipleChoiceQuestion(
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
    ))
}