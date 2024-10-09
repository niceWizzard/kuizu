package com.coderizzard.quizzerist.presentation.appbar

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import com.coderizzard.quizzerist.presentation.navigation.HomeRoute

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun HomeAppBar(routeRes: HomeRoute) {
    TopAppBar(
        title = { Text(routeRes.displayName) },
    )
}