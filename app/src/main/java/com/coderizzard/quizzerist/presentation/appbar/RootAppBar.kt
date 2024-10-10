package com.coderizzard.quizzerist.presentation.appbar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.coderizzard.core.data.navigation.RootRoute


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun RootAppBar(
    navController: NavController,
    route: RootRoute,
) {
    when(route) {
        RootRoute.Home -> throw Exception("Home route should not be handled here.")
        is RootRoute.QuizSession -> {
            TopAppBar(
                title = { },
                actions = {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        IconButton(
                            onClick = {
                                navController.popBackStack()
                            }
                        ) {
                            Icon(
                                Icons.AutoMirrored.Default.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                        IconButton(
                            onClick = {}
                        ) {
                            Icon(Icons.Default.MoreVert, contentDescription = "more")
                        }
                    }
                }
            )
        }

        RootRoute.QuizAdd -> {
            TopAppBar(
                title = {  },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }


    }
}