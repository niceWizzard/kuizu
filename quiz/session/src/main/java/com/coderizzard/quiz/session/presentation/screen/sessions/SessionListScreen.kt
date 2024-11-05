package com.coderizzard.quiz.session.presentation.screen.sessions

import android.app.Activity
import android.widget.Toast
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelStoreOwner
import coil.compose.AsyncImage
import com.coderizzard.core.data.AsyncData
import com.coderizzard.core.data.model.Quiz
import com.coderizzard.core.data.model.session.QuizSession
import com.coderizzard.core.data.navigation.RootRoute
import java.time.LocalDateTime
import java.util.UUID

@Composable
fun SessionListScreen() {
    val activity = LocalContext.current as Activity as ViewModelStoreOwner
    val viewModel : SessionsScreenViewModel = hiltViewModel(activity)
    val sessionList by viewModel.sessionList.collectAsState(AsyncData.Loading)
    Content(
        sessionList = sessionList,
        onPlayButtonClick = { id, autoStart ->
            viewModel.navigationManager.navigateTo(
                RootRoute.QuizSession(id, autoStart)
            )
        },
        onEvent = viewModel::onEvent,
    )
}

@Composable
private fun Content(
    sessionList: AsyncData<List<QuizSession>>,
    onPlayButtonClick: (id : String, autoStart: Boolean) -> Unit,
    onEvent: (e : ScreenEvent) -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        when(sessionList) {
            is AsyncData.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(sessionList.message)
                }
            }
            AsyncData.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }
            is AsyncData.Success -> {
                LazyColumn (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(
                        12.dp,
                    )
                ) {
                    item {
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            "All sessions",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                        HorizontalDivider()
                    }
                    val list = sessionList.data
                    item {
                        if(list.isEmpty()) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("No sessions yet.")
                            }
                        }
                    }
                    items(
                        items = list,
                    ) { session ->
                        Card(
                            onClick = {
                                onPlayButtonClick(session.quizId, false)
                            }
                        ) {
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
                                    modifier = Modifier
                                        .heightIn(
                                            max = 124.dp,
                                        )
                                        .fillMaxWidth(0.2F)
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
                                        text = "${session.currentQuestionIndex }/${session.quiz.questions.size} questions answered",
                                        fontWeight = FontWeight.Light,
                                        fontSize = 12.sp,
                                    )
                                }
                                Column {
                                    IconButton(
                                        onClick = {
                                            onPlayButtonClick(session.quizId,true)
                                        },
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Icon(
                                            Icons.Default.PlayArrow,
                                            contentDescription = "Continue",
                                            modifier = Modifier.fillMaxSize()
                                        )
                                    }
                                    IconButton(
                                        onClick = {
                                            onEvent(ScreenEvent.OnDelete(session.quizId))
                                        },
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Icon(
                                            Icons.Default.Delete,
                                            contentDescription = "Delete",
                                            modifier = Modifier.fillMaxSize()
                                        )
                                    }
                                }
                            }
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(12.dp))
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
        onPlayButtonClick = {_,_ -> },
        sessionList = AsyncData.Success(
            List(10) {
                QuizSession(
                    questionOrder = listOf("lkjsdf"),
                    quiz = Quiz(
                        id = UUID.randomUUID().toString(),
                        remoteId = "",
                        name = "Some preview quiz $it",
                        author = "Some author $it",
                        allQuestions = emptyList(),
                        createdAt = LocalDateTime.now(),
                        localImagePath = "",
                        imageLink = ""
                    ),
                    quizId = UUID.randomUUID().toString(),
                    startedAt = LocalDateTime.now(),
                    currentQuestionIndex = 0,
                )
            },
        ),
        onEvent = {}
    )
}

@Preview
@Composable
private fun SessionListLoadingPreview() {
    Content(
        sessionList = AsyncData.Loading,
        onPlayButtonClick = {_,_ -> },
        onEvent = {}
    )
}