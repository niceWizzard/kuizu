package com.coderizzard.kuizu.presentation

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
import com.coderizzard.kuizu.presentation.action_button.AppActionButton
import com.coderizzard.kuizu.presentation.appbar.TopBar
import com.coderizzard.kuizu.presentation.navigation.NavBar
import com.coderizzard.kuizu.presentation.navigation.NavGraph
import com.coderizzard.kuizu.ui.theme.KuizuTheme
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
            KuizuTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = { NavBar(navController = navController) },
                    topBar = { TopBar(navController = navController) },
                    floatingActionButton = { AppActionButton(navController) }
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

