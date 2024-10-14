package com.coderizzard.core.data.navigation

import androidx.navigation.NavController
import androidx.navigation.toRoute
import javax.inject.Inject


class NavigationManager @Inject constructor() {
    lateinit var navController: NavController

    fun navigateTo(
        route : NavRoute
    ) {
        navController.navigate(route)
    }

    inline fun <reified T : NavRoute> getRouteData(): T? {
        return navController.currentBackStackEntry?.toRoute<T>()

    }

    fun popBackStack() {
        navController.popBackStack()
    }

}