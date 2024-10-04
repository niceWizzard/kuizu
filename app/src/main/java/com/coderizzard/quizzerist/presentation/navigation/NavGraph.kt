package com.coderizzard.quizzerist.presentation.navigation

import android.app.Activity
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
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

@Composable
fun NavGraph(
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = NavRoute.QUIZ.route
    ) {
        composable(NavRoute.QUIZ.route) {
            HomeScreen(
                navController = navController,

            )
        }
        composable(NavRoute.SETTINGS.route) {
            Text("SEttings ")
        }
        composable(NavRoute.SESSIONS.route) {
            Text("SESSIONS")
        }
    }
}

data class NavRoute(
    val route : String,
    val displayName : String,
    val imageVector: ImageVector,

) {
    companion object {
        val QUIZ = NavRoute("home", "Quizzes",Icons.Filled.Home)
        val SETTINGS = NavRoute("settings", "Settings",Icons.Filled.Settings)
        val SESSIONS = NavRoute("session", "Sessions", Icons.Default.Build)
        val allRoutes = listOf(SESSIONS, QUIZ, SETTINGS)

    }


}


