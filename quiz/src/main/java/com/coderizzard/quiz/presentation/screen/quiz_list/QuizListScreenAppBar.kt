package com.coderizzard.quiz.presentation.screen.quiz_list

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.coderizzard.core.data.navigation.NavRoute
import com.coderizzard.core.data.navigation.RootRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizListScreenAppBar(
    onAddButtonClick: () -> Unit,
    route : NavRoute
) {
    TopAppBar(
        title = { Text(route.displayName) },
        actions = {
            IconButton(
                onClick = {
                    onAddButtonClick()
                }
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Add"
                )
            }
        }
    )
}