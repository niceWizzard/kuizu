package com.coderizzard.quizzerist.presentation.screens.homescreen

import android.app.Activity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
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
    HomeScreenContent(
        searchString = searchString,
        onEvent = homeScreenViewModel::onEvent
    )
}

@Composable
private fun HomeScreenContent(
    searchString : String,
    onEvent : (HomeScreenEvent) -> Unit,
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
            }
        )

    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreenContent(
        searchString = "",
        onEvent = {

        }
    )
}