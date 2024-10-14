package com.coderizzard.quiz.presentation.screen.add_quiz

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import com.coderizzard.network.data.model.ExtractedIdentificationQuestion
import com.coderizzard.network.data.model.ExtractedMultipleChoiceQuestion

@Composable
fun AddQuizScreen() {
    val activity = LocalContext.current as Activity as ViewModelStoreOwner
    val addQuizScreenViewModel: AddQuizScreenViewModel = hiltViewModel(activity)
    val searchString = addQuizScreenViewModel.searchString.value
    val searchQuizState by addQuizScreenViewModel.searchQuiz.collectAsState()
    AddQuizScreenContent(
        searchString = searchString,
        onEvent = addQuizScreenViewModel::onEvent,
        searchQuizState = searchQuizState
    )
}

@Composable
private fun AddQuizScreenContent(
    searchString : String,
    onEvent : (AddQuizEvent) -> Unit,
    searchQuizState: SearchQuizState,

    ) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp, vertical = 6.dp),
        verticalArrangement = Arrangement.spacedBy(
            12.dp,
        )
    ) {
        Text("Home Screen")

        OutlinedTextField(
            value = searchString,
            onValueChange = { newText ->
                onEvent(AddQuizEvent.OnSearchChange(newText))
            },
            label = {
                Text("Quiz id or Url")
            },
            placeholder = {
                Text("https://quizizz.com/quiz/<idhere>")
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
        )
        ElevatedButton(
            onClick = {
                onEvent(AddQuizEvent.OnSearchSubmit)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Search")
        }

        when(searchQuizState) {
            SearchQuizState.Default -> {
                Text("Quiz will appear here.")
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
            is SearchQuizState.Success -> {
                val quiz = searchQuizState.data
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement
                        .spacedBy(12.dp)
                ) {
                    Text(
                        quiz.name,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Row {
                        Text(
                            "Created by: ${quiz.author}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Light
                        )
                        Text("Created at: ${quiz.createdAt}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Light
                        )
                    }
                    Spacer(modifier = Modifier.height(32.dp))
                    quiz.questionList.map { q ->
                        Card {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 12.dp, vertical = 8.dp),
                            ) {
                                Text(
                                    q.text,
                                    fontSize = 18.sp,
                                )
                                Spacer(Modifier.height(12.dp))
                                when(q) {
                                    is ExtractedIdentificationQuestion -> {
                                        Text("Answer: ${q.answer}")
                                    }
                                    is ExtractedMultipleChoiceQuestion -> {
                                        Column {
                                            q.options.mapIndexed { index, opt ->
                                                val isCorrect = q.answer.contains(index)
                                                Text(buildString {
                                                    if (isCorrect)
                                                        append( "Correct - ")
                                                    append(opt)
                                                })
                                            }
                                        }
                                    }
                                }
                            }
                        }

                    }
                }
            }

            is SearchQuizState.Invalid -> {
                Text(
                    searchQuizState.message,
                    color = MaterialTheme.colorScheme.error,
                )
            }
        }

    }

}

@Preview
@Composable
private fun AddQuizScreenPreview() {
    AddQuizScreenContent(
        searchString = "",
        onEvent = {

        },
        searchQuizState = SearchQuizState.Default
    )

}