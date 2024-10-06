package com.coderizzard.quizzerist.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

@Serializable
sealed class RootNav(
    val displayName: String=""
) {
    @Serializable
    data object Home : RootNav("Home")

    @Serializable
    data class QuizSession(val id : String= "") : RootNav("Quiz Session")
}

@Serializable
sealed class NavRoute(
    val displayName : String = "",
) {
    @Serializable
    data object Quiz : NavRoute("Quiz")
    @Serializable
    data object Settings : NavRoute("Settings")
    @Serializable
    data object Sessions : NavRoute("Sessions")

    companion object {
        val allRoutes = listOf(
            Quiz,
            Sessions,
            Settings,
        )
        fun getImage(a : NavRoute): ImageVector {
            return when(a) {
                is Quiz -> Icons.Default.Home
                Sessions -> Icons.Default.FavoriteBorder
                Settings -> Icons.Default.Settings
            }
        }
    }
}