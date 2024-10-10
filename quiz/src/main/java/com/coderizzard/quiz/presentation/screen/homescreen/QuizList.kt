package com.coderizzard.quiz.presentation.screen.homescreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.coderizzard.core.data.model.Quiz
import com.coderizzard.core.data.model.question.Question

@Composable
internal fun QuizList(quizList: List<Quiz>) {
    Column(
        modifier = Modifier.verticalScroll(
            rememberScrollState()
        ),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (quizList.isEmpty()) {
            Text("No quizzes yet.")
        }
        quizList.map { quiz ->
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(
                        12.dp, 8.dp
                    )
                ) {
                    Text(
                        quiz.name,
                        fontSize = 18.sp
                    )
                    Text(
                        quiz.author, fontWeight = FontWeight.Light, fontSize = 12.sp,
                    )
                    Spacer(Modifier.height(12.dp))
                    HorizontalDivider(modifier = Modifier.height(12.dp))
                    Text("Questions (${quiz.questions.size})")
                    QuestionList(quiz.questions)
                }
            }
        }

    }
}

@Composable
private fun QuestionList(questions : List<Question>) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        questions.mapIndexed { i, q ->
            Question(q)
            if(i != questions.size - 1) {
                HorizontalDivider()
            }
        }
    }
}

