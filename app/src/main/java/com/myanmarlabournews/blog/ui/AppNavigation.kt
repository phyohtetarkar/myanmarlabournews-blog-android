package com.myanmarlabournews.blog.ui

import androidx.navigation.NavHostController

sealed class AppDestination(val route: String) {

    object Splash : AppDestination("splash")

    object Main : AppDestination("main") {
        object Home : AppDestination("home")

        object Search : AppDestination("search")

        object Menu : AppDestination("menu")
    }

    object About : AppDestination("about")

    object PostsByType : AppDestination("postsByType/{type}")

    object PostsByTag : AppDestination("postsByTag/{slug}")

    object Post : AppDestination("posts/{slug}")

}

class MainNavigationActions(navController: NavHostController) {
    val navigateToHome: () -> Unit = {
        navController.navigate(AppDestination.Main.Home.route) {
            popUpTo(navController.graph.startDestinationId) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    val navigateToSearch: () -> Unit = {
        navController.navigate(AppDestination.Main.Search.route) {
            popUpTo(navController.graph.startDestinationId) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    val navigateToMenu: () -> Unit = {
        navController.navigate(AppDestination.Main.Menu.route) {
            popUpTo(navController.graph.startDestinationId) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}

class AppNavigationActions(navController: NavHostController) {

    val navigateToMain: () -> Unit = {
        navController.navigate(AppDestination.Main.route) {
            popUpTo(AppDestination.Splash.route) {
                inclusive = true
            }
        }
    }

    val navigateToAbout: () -> Unit = {
        navController.navigate(AppDestination.About.route)
    }
}