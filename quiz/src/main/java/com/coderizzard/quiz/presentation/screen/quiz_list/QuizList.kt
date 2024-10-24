package com.coderizzard.quiz.presentation.screen.quiz_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.coderizzard.core.data.model.Quiz

@Composable
internal fun QuizList(quizList: QuizListState, onQuizClick: (id: String) -> Unit) {
    when(quizList) {
        QuizListState.Loading -> {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is QuizListState.Error -> {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text("Some error happened. ${quizList.message}")
            }
        }
        is QuizListState.Success -> {
            val list = quizList.data
            if (list.isEmpty()) {
                Text("No quizzes yet.")
            }
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    items = list,
                    key = Quiz::id
                ) { quiz ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {onQuizClick(quiz.id)}
                    ) {
                        Column(
                            modifier = Modifier.padding(
                                12.dp, 8.dp
                            )
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(12.dp, alignment = Alignment.Start)
                            ) {
                                AsyncImage(
                                    model = quiz.localImagePath,
                                    contentDescription = "Nice",
                                    modifier = Modifier.heightIn(min = 64.dp, max=90.dp),
                                )
                                Column {
                                    Text(
                                        quiz.name,
                                        fontSize = 18.sp
                                    )
                                    Text(
                                        quiz.author, fontWeight = FontWeight.Light, fontSize = 12.sp,
                                    )
                                }
                            }
                            Spacer(Modifier.height(12.dp))
                            HorizontalDivider(modifier = Modifier.height(12.dp))
                            Text("Questions (${quiz.questions.size})")
                        }
                    }
                }
            }
        }
    }
}



