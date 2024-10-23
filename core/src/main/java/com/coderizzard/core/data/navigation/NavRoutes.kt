package com.coderizzard.core.data.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
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

    @Serializable
    data class Quiz(val id : String="") : RootRoute("Quiz - $id")

    companion object {
        val routes = listOf(
            Home,
            QuizSession(),
            QuizAdd,
            Quiz()
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
        fun getImage(a : HomeRoute, isActive : Boolean = false): ImageVector {
            return when(a) {
                is Quiz -> if(isActive) Icons.Filled.Home else Icons.Outlined.Home
                Sessions -> if(isActive) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder
                Settings -> if(isActive) Icons.Filled.Settings else Icons.Outlined.Settings
            }
        }
    }
}