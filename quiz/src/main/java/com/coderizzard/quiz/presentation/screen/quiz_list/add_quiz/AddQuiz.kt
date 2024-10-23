package com.coderizzard.quiz.presentation.screen.quiz_list.add_quiz

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.coderizzard.core.data.model.Quiz
import java.time.LocalDateTime

@Composable
fun AddQuizDialog(
    onDismissRequest: () -> Unit,
    addQuizFieldFocusRequester: FocusRequester
) {
    val addQuizViewModel: AddQuizScreenViewModel = hiltViewModel()
    val searchString = addQuizViewModel.searchString.value
    val searchQuizState by addQuizViewModel.searchQuiz.collectAsState()
    DisposableEffect(Unit) {
        onDispose {
            addQuizViewModel.onEvent(AddQuizEvent.OnReset)
        }
    }
    AddQuizDialogContent(
        searchString = searchString,
        onEvent = addQuizViewModel::onEvent,
        searchQuizState = searchQuizState,
        onDismissRequest = onDismissRequest,
        focusRequester = addQuizFieldFocusRequester
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddQuizDialogContent(
    searchString : String,
    onEvent : (AddQuizEvent) -> Unit,
    searchQuizState: SearchQuizState,
    onDismissRequest: () -> Unit,
    focusRequester: FocusRequester
    ) {
    val context = LocalContext.current
    BasicAlertDialog(
        onDismissRequest = {
            if (searchQuizState is SearchQuizState.Error
                || searchQuizState is SearchQuizState.Default) {
                onDismissRequest()
            }
        },
    ) {
        val primaryButtonColors = ButtonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = MaterialTheme.colorScheme.onSurface.copy(
                alpha = 0.12f
            ), // Disabled background color
            disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(
                alpha = 0.38f
            ),
        )
        Surface {
            Column(
                modifier = Modifier
                    .padding(horizontal = 18.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(
                    12.dp,
                )
            ) {
                when(searchQuizState) {
                    SearchQuizState.Default -> {
                        Text(
                            "Add a quiz",
                            fontSize = 24.sp
                        )
                        OutlinedTextField(
                            value = searchString,
                            onValueChange = { newText ->
                                onEvent(AddQuizEvent.OnSearchChange(newText))
                            },
                            label = {
                                Text("Quiz id or Url", maxLines = 1)
                            },
                            placeholder = {
                                Text("https://quizizz.com/quiz/<idhere>")
                            },
                            modifier = Modifier.fillMaxWidth().focusRequester(
                                focusRequester
                            ),
                            singleLine = true,
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp, alignment = Alignment.End)
                        ) {
                            ElevatedButton(
                                onClick = {
                                    onEvent(AddQuizEvent.OnReset)
                                    onDismissRequest()
                                }
                            ) {
                                Text("Cancel")
                            }

                            ElevatedButton(
                                onClick = {
                                    onEvent(
                                        AddQuizEvent.OnSearchSubmit(onDismissRequest, context)
                                    )
                                },
                                colors = primaryButtonColors,
                            ) {
                                Text("Add")
                            }
                        }
                    }
                    is SearchQuizState.Error -> {
                        Text(
                            "Something went wrong. \n${searchQuizState.err}",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                    SearchQuizState.Fetching -> {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(
                                12.dp,
                                alignment = Alignment.CenterHorizontally
                            ),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            CircularProgressIndicator()
                            Text("Fetching the quiz...")
                        }
                    }
                    is SearchQuizState.Invalid -> {
                        Text(
                            searchQuizState.message,
                            color = MaterialTheme.colorScheme.error,
                        )
                    }
                    is SearchQuizState.DuplicatedQuiz -> {
                        val quiz = searchQuizState.quiz
                        Text(
                            "Duplicate quiz found.",
                            fontSize = 24.sp
                        )
                        Text(
                            "Quiz with quizizz id <${quiz.remoteId}> already exists. Are you sure you want to re-add this?"
                        )
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp, alignment = Alignment.End),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            ElevatedButton(
                                onClick = {onDismissRequest()},
                                colors = primaryButtonColors
                            ) {
                                Text("Abort")
                            }
                            ElevatedButton(
                                onClick = {
                                    onEvent(
                                        AddQuizEvent.OnDuplicatedQuizAdd(
                                            searchQuizState.quiz,
                                            action = {
                                                onEvent(AddQuizEvent.OnReset)
                                                onDismissRequest()
                                            },
                                            context
                                        )
                                    )
                                }
                            ) {
                                Text("Continue")
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
private fun AddQuizScreenPreview() {
    AddQuizDialogContent(
        searchString = "",
        onEvent = {

        },
        onDismissRequest = {},
        focusRequester = FocusRequester(),
        searchQuizState = SearchQuizState.DuplicatedQuiz(
            Quiz(
                id = "1",
                remoteId = "1",
                name = "Duplicated Quiz",
                author = "Some author",
                questions = emptyList(),
                createdAt = LocalDateTime.now(),
                imageLink = ""
            ),
        ),
    )

}