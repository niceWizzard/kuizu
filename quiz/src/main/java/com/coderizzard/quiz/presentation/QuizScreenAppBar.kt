package com.coderizzard.quiz.presentation

import android.widget.Toast
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import com.coderizzard.quiz.presentation.screen.quiz.QuizScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreenAppBar(
    backStackEntry: NavBackStackEntry
) {
    var isMoreShown by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current
    val quizScreenViewModel : QuizScreenViewModel = hiltViewModel(backStackEntry)
    TopAppBar(
        title = {  },
        navigationIcon = {
            IconButton(
                onClick = {
                    quizScreenViewModel.navigationManager.popBackStack()
                }
            ) {
                Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Back")
            }
        },
        actions = {
            IconButton(
                onClick = {
                    isMoreShown = !isMoreShown
                }
            ) {
                Icon(Icons.Default.MoreVert, contentDescription = "More")
                DropdownMenu(
                    expanded = isMoreShown,
                    onDismissRequest = {
                        isMoreShown = false
                    },
                ) {
                    DropdownMenuItem(
                        text = { Text("Delete Quiz") },
                        onClick = {
                            isMoreShown = false
                            Toast.makeText(context, "Deleting ${quizScreenViewModel.routeParams().id}", Toast.LENGTH_SHORT).show()
                            quizScreenViewModel.deleteCurrentQuiz()
                            quizScreenViewModel.navigationManager.popBackStack()
                        }
                    )
                }
            }

        }
    )
}