package com.coderizzard.quizzerist.presentation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.coderizzard.quizzerist.presentation.navigation.NavRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavController) {
    val routeList = NavRoute.allRoutes
    val navState by navController.currentBackStackEntryAsState()

    val routeTitle = routeList.find{
        navState?.destination?.route == it.route
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