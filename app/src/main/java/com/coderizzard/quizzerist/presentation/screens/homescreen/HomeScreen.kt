package com.coderizzard.quizzerist.presentation.screens.homescreen

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavController
import com.coderizzard.database.data.database.model.QuizEntity

@Composable
fun HomeScreen(
    navController: NavController,
) {
    val activity = LocalContext.current as Activity as ViewModelStoreOwner
    val homeScreenViewModel: HomeScreenViewModel = hiltViewModel(activity)
    val quizList by homeScreenViewModel.allQuizzes.collectAsState(emptyList())
    HomeScreenContent(
        quizList
    )
}

@Composable
private fun HomeScreenContent(quizList: List<QuizEntity>) {
    Column {
        Text("All Quizzes")
        Column(
            modifier = Modifier.verticalScroll(
                rememberScrollState()
            ),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            quizList.map {
                Text(it.toString())
            }
            if(quizList.isEmpty()) {
                Text("No quizzes yet.")
            }
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreenContent(quizList = emptyList())
}