package com.coderizzard.quizzerist.presentation.navigation

import android.app.Activity
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.coderizzard.quizzerist.presentation.screens.homescreen.HomeScreen
import com.coderizzard.quizzerist.presentation.screens.homescreen.HomeScreenViewModel
import kotlinx.serialization.Serializable

@Composable
fun NavGraph(
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = NavRoute.Quiz,

    ) {
        composable<NavRoute.Quiz>(

        ) {
            HomeScreen(
                navController = navController,

            )
        }
        composable<NavRoute.Settings> {
            Text("SEttings ")
        }
        composable<NavRoute.Sessions> {
            Text("SESSIONS")
        }
    }
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


