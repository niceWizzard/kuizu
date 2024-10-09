package com.coderizzard.quizzerist.presentation.appbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.coderizzard.quizzerist.presentation.navigation.HomeRoute
import com.coderizzard.quizzerist.presentation.navigation.RootRoute

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun HomeAppBar(
    route: HomeRoute,
    navController: NavController
) {
    when(route) {
        HomeRoute.Quiz -> {
            TopAppBar(
                title = { Text(route.displayName) },
                actions = {
                    IconButton(
                        onClick = {
                            navController.navigate(RootRoute.QuizAdd)
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
        HomeRoute.Sessions, HomeRoute.Settings -> {
            TopAppBar(
                title = { Text(route.displayName) },
            )
        }
    }
}