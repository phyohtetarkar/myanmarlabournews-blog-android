package com.myanmarlabournews.blog.ui

import androidx.navigation.NavHostController

object BaseDestinations {
    const val HOME_ROUTE = "home"
    const val SEARCH_ROUTE = "search"
    const val MENU_ROUTE = "menu"
}

class BaseNavigationActions(navController: NavHostController) {
    val navigateToHome: () -> Unit = {
        navController.navigate(BaseDestinations.HOME_ROUTE) {
            popUpTo(navController.graph.startDestinationId) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    val navigateToSearch: () -> Unit = {
        navController.navigate(BaseDestinations.SEARCH_ROUTE) {
            popUpTo(navController.graph.startDestinationId) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    val navigateToMenu: () -> Unit = {
        navController.navigate(BaseDestinations.MENU_ROUTE) {
            popUpTo(navController.graph.startDestinationId) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}