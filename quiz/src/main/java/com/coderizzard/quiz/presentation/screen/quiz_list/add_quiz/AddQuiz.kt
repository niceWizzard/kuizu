package com.coderizzard.quiz.presentation.screen.quiz_list.add_quiz

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun AddQuizDialog() {
    val addQuizViewModel: AddQuizScreenViewModel = hiltViewModel()
    val searchString = addQuizViewModel.searchString.value
    val searchQuizState by addQuizViewModel.searchQuiz.collectAsState()
    AddQuizDialogContent(
        searchString = searchString,
        onEvent = addQuizViewModel::onEvent,
        searchQuizState = searchQuizState
    )
}

@Composable
private fun AddQuizDialogContent(
    searchString : String,
    onEvent : (AddQuizEvent) -> Unit,
    searchQuizState: SearchQuizState,

    ) {
    val focusRequester = remember {FocusRequester()}
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
    Column(
        modifier = Modifier
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
            modifier = Modifier.fillMaxWidth().focusRequester(focusRequester),
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
    AddQuizDialogContent(
        searchString = "",
        onEvent = {

        },
        searchQuizState = SearchQuizState.Default
    )

}