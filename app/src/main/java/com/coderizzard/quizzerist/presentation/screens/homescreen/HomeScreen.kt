package com.coderizzard.quizzerist.presentation.screens.homescreen

import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavController

@Composable
fun HomeScreen(
    navController: NavController,
    homeScreenViewModel: HomeScreenViewModel
) {

    val quizJson by homeScreenViewModel.quizJson.collectAsState()
    HomeScreenContent(
        quizJson = quizJson.toString()
    )
}

@Composable
private fun HomeScreenContent(
    quizJson : String
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text("Home Screen")
        Text(
            quizJson,
            modifier = Modifier.verticalScroll(rememberScrollState())
        )
    }
}