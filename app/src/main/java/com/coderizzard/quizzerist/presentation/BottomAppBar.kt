package com.coderizzard.quizzerist.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun BottomAppBar(
    navController: NavController
) {
    val routes = listOf(
        NavRoute.QUIZ,
        NavRoute.SETTINGS
    )
    androidx.compose.material3.BottomAppBar{
        Row(
            horizontalArrangement = Arrangement.spacedBy(
                12.dp,
                alignment = Alignment.CenterHorizontally,
            ),
            modifier = Modifier.fillMaxWidth(),
        ) {
            for(route in routes) {
                IconButton(
                    onClick = {
                        navController.navigate(route.route)
                    }
                ) {
                    Icon(route.imageVector, contentDescription = "${route.route} Screen")
                }
            }
        }
    }
}