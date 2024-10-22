package com.coderizzard.quizzerist.presentation.action_button

import android.widget.Toast
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.coderizzard.core.data.navigation.RootRoute

@Composable
fun RootRouteActionButton(
    route : RootRoute,
) {
    val context = LocalContext.current
    when(route) {
        is RootRoute.Quiz -> {
            FloatingActionButton(
                onClick = {
                    Toast.makeText(context , "To be implemented", Toast.LENGTH_SHORT).show()
                }
            ) {
                Icon(
                    Icons.Default.PlayArrow,
                    "Start quiz"
                )
            }
        }
        else -> {}
    }
}