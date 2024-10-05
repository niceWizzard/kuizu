package com.coderizzard.quizzerist.presentation.screens.homescreen

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavController

@Composable
fun HomeScreen(
    navController: NavController,
) {
    val activity = LocalContext.current as Activity as ViewModelStoreOwner
    val homeScreenViewModel: HomeScreenViewModel = hiltViewModel(activity)
    val searchString = homeScreenViewModel.searchString.value
    val searchQuizState by homeScreenViewModel.searchQuiz.collectAsState()
    HomeScreenContent(
        searchString = searchString,
        onEvent = homeScreenViewModel::onEvent,
        searchQuizState = searchQuizState
    )
}

@Composable
private fun HomeScreenContent(
    searchString : String,
    onEvent : (HomeScreenEvent) -> Unit,
    searchQuizState: SearchQuizState,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text("Home Screen")

        OutlinedTextField(
            value = searchString,
            onValueChange = { newText ->
                onEvent(HomeScreenEvent.OnSearchChange(newText))
            },
            label = {
                Text("Quiz id or Url")
            },
            placeholder = {
                Text("https://quizizz.com/quiz/<idhere>")
            },
            modifier = Modifier.fillMaxWidth()
        )
        ElevatedButton(
            onClick = {
                onEvent(HomeScreenEvent.OnSearchSubmit)
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
                Text(searchQuizState.data.toString())
            }
        }

    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreenContent(
        searchString = "",
        onEvent = {

        },
        searchQuizState = SearchQuizState.Default
    )
}