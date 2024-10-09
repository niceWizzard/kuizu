package com.coderizzard.quizzerist.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable


sealed interface NavRoute {
    val displayName : String
}


@Serializable
sealed class RootRoute(
    override val displayName: String=""
) : NavRoute {
    @Serializable
    data object Home : RootRoute("Home")

    @Serializable
    data class QuizSession(val id : String= "") : RootRoute("Quiz Session")

    @Serializable
    data object QuizAdd : RootRoute("Add Quiz")

    companion object {
        val routes = listOf(
            Home,
            QuizSession(),
            QuizAdd
        )
        val allRoutes =  routes + HomeRoute.allRoutes
    }
}


@Serializable
sealed class HomeRoute(
    override val displayName : String = "",
) : NavRoute {
    @Serializable
    data object Quiz : HomeRoute("Quiz")
    @Serializable
    data object Settings : HomeRoute("Settings")
    @Serializable
    data object Sessions : HomeRoute("Sessions")

    companion object {
        val allRoutes = listOf(
            Quiz,
            Sessions,
            Settings,
        )
        fun getImage(a : HomeRoute): ImageVector {
            return when(a) {
                is Quiz -> Icons.Default.Home
                Sessions -> Icons.Default.FavoriteBorder
                Settings -> Icons.Default.Settings
            }
        }
    }
}