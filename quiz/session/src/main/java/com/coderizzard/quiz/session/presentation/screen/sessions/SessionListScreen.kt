package com.coderizzard.quiz.session.presentation.screen.sessions

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidthIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelStoreOwner
import coil.compose.AsyncImage
import com.coderizzard.core.data.model.Quiz
import com.coderizzard.core.data.model.session.QuizSession
import java.time.LocalDateTime
import java.util.UUID

@Composable
fun SessionListScreen() {
    val activity = LocalContext.current as Activity as ViewModelStoreOwner
    val viewModel : SessionsScreenViewModel = hiltViewModel(activity)
    val sessionList by viewModel.sessionList.collectAsState(emptyList())
    Content(
        sessionList = sessionList
    )
}

@Composable
private fun Content(
    sessionList: List<QuizSession>
) {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn (
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(
                12.dp,
            )
        ) {
            items(
                items = sessionList,
            ) { session ->
                    Card {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 124.dp)
                                .padding(vertical = 12.dp, horizontal = 16.dp),
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            AsyncImage(
                                model = session.quiz.localImagePath,
                                contentDescription = "Quiz session",
                                modifier = Modifier.heightIn(
                                    max = 124.dp,
                                ).fillMaxWidth(0.2F)
                            )
                            Column(
                                verticalArrangement = Arrangement.spacedBy(
                                    12.dp,
                                ),
                                modifier = Modifier
                                    .fillMaxWidth(0.8F)
                                    .padding(horizontal = 12.dp),
                            ) {
                                Text(session.quiz.name)
                                Text(
                                    text = "${session.currentQuestionIndex + 1}/${session.quiz.questions.size} questions answered",
                                    fontWeight = FontWeight.Light,
                                    fontSize = 12.sp,
                                )
                            }
                            val context = LocalContext.current
                            IconButton(
                                onClick = {
                                    Toast.makeText(context, "To be implemented", Toast.LENGTH_SHORT).show()
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Icon(
                                    Icons.Default.PlayArrow,
                                    contentDescription = "Continue",
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }
                    }
            }
        }

    }
}

@Preview
@Composable
private fun SessionListPreview() {
    Content(
        sessionList = List(5) {
            QuizSession(
                id = UUID.randomUUID().toString(),
                questionOrder = listOf("lkjsdf"),
                quiz = Quiz(
                    id = UUID.randomUUID().toString(),
                    remoteId = "",
                    name = "Some preview quiz $it",
                    author = "Some author $it",
                    questions = emptyList(),
                    createdAt = LocalDateTime.now(),
                    localImagePath = "",
                    imageLink = ""
                ),
               quizId = UUID.randomUUID().toString(),
                startedAt = LocalDateTime.now(),
                currentQuestionIndex = 0,
            )
        }
    )
}