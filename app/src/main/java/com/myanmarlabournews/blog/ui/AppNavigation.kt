package com.myanmarlabournews.blog.ui

import androidx.navigation.NavHostController
import com.myanmarlabournews.blog.model.Author
import com.myanmarlabournews.blog.model.Post
import com.myanmarlabournews.blog.model.Tag

sealed class AppDestination(val route: String) {

    data object Splash : AppDestination("splash")

    data object Main : AppDestination("main") {
        data object Home : AppDestination("home")

        data object Search : AppDestination("search")

        data object Menu : AppDestination("menu")
    }

    data object About : AppDestination("about")

    data object PostsByType : AppDestination("postsByType/{type}")

    data object PostsByTag : AppDestination("postsByTag?tagId={tagId}&tagName={tagName}")

    data object PostsByAuthor :
        AppDestination("postsByAuthor?authorId={authorId}&authorName={authorName}")

    data object Post : AppDestination("posts/{slug}")

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

    val navigateToPost: (String) -> Unit = { slug ->
        navController.navigate("posts/${slug}")
    }

    val navigateToPostsByType: (Post.Type) -> Unit = { type ->
        navController.navigate("postsByType/${type.name}")
    }

    val navigateToPostsByTag: (Tag) -> Unit = { tag ->
        navController.navigate("postsByTag?tagId=${tag.id}&tagName=${tag.name}")
    }

    val navigateToPostsByAuthor: (Author) -> Unit = { author ->
        navController.navigate("postsByAuthor?authorId=${author.id}&authorName=${author.name}")
    }

    val navigateUp: () -> Unit = {
        navController.navigateUp()
    }

}