package com.coderizzard.quizzerist.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.coderizzard.core.data.navigation.NavigationManager
import com.coderizzard.quizzerist.presentation.appbar.TopBar
import com.coderizzard.quizzerist.presentation.navigation.NavBar
import com.coderizzard.quizzerist.presentation.navigation.NavGraph
import com.coderizzard.quizzerist.ui.theme.QuizzeristTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var navigationManager: NavigationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            LaunchedEffect(Unit) {
                navigationManager.navController = navController
            }
            QuizzeristTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = { NavBar(navController = navController) },
                    topBar = { TopBar(navController = navController) }
                ) { innerPadding ->
                    Surface(
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        NavGraph(navController = navController)
                    }
                }
            }
        }
    }
}

