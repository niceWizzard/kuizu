package com.coderizzard.kuizu.presentation.appbar

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.coderizzard.core.data.navigation.HomeRoute

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun HomeAppBar(
    route: HomeRoute,
    navController: NavController
) {
    when(route) {
        else -> {
            TopAppBar(
                title = { Text(route.displayName) },
            )
        }
    }
}