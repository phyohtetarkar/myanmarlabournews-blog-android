package com.myanmarlabournews.blog.ui

import androidx.navigation.NavHostController

sealed class AppDestination(val route: String) {

    object Home : AppDestination("home")

    object Search : AppDestination("search")

    object Menu : AppDestination("menu")

    object About : AppDestination("about")

    object PostsByType : AppDestination("postsByType/{type}")

    object PostsByTag : AppDestination("postsByTag/{slug}")

    object Post : AppDestination("posts/{slug}")

}

class AppNavigationActions(navController: NavHostController) {
    val navigateToHome: () -> Unit = {
        navController.navigate(AppDestination.Home.route) {
            popUpTo(navController.graph.startDestinationId) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    val navigateToSearch: () -> Unit = {
        navController.navigate(AppDestination.Search.route) {
            popUpTo(navController.graph.startDestinationId) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    val navigateToMenu: () -> Unit = {
        navController.navigate(AppDestination.Menu.route) {
            popUpTo(navController.graph.startDestinationId) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}