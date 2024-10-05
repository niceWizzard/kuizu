package com.coderizzard.quizzerist.presentation

import android.os.Build
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.coderizzard.quizzerist.presentation.navigation.NavRoute
import java.io.Serializable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavController) {
    val routeList = NavRoute.allRoutes

    val backStackEntry by navController.currentBackStackEntryAsState()

    val activeRoute = backStackEntry?.toRoute<NavRoute>()

    val routeTitle = routeList.find{ it ->
         it == activeRoute
    }.let {
        it?.displayName ?: "Route Title"
    }
    TopAppBar(
        title = { Text(routeTitle) },
    )
}


@Preview
@Composable
private fun PreviewTopBar() {
    TopBar(rememberNavController())
}

