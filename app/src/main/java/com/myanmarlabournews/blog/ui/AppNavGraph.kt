package com.myanmarlabournews.blog.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.myanmarlabournews.blog.ServiceLocator
import com.myanmarlabournews.blog.model.Post
import com.myanmarlabournews.blog.ui.about.AboutScreen

@Composable
fun AppNavGraph(
    serviceLocator: ServiceLocator,
    navigationActions: AppNavigationActions,
    locale: Post.Lang = Post.Lang.MM,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = AppDestination.Main.route
) {
    var selectedItemIndex by remember { mutableStateOf(0) }

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {

        composable(AppDestination.Main.route) {
            MainRoute(
                serviceLocator = serviceLocator,
                navigationActions = navigationActions,
                locale = locale,
                selectedBottomNavIndex = selectedItemIndex,
                onBottomNavChange = { v ->
                    selectedItemIndex = v
                },
            )
        }
        composable(AppDestination.About.route) {
            AboutScreen(
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SlideAnimation(
    content: @Composable () -> Unit
) {
    val visibleState = MutableTransitionState(false).apply {
        // Start the animation immediately.
        targetState = true
    }
    AnimatedVisibility(
        visibleState = visibleState,
        enter = slideInHorizontally(
            initialOffsetX = { fw -> fw }
        ),
        exit = slideOutHorizontally(
            targetOffsetX = { fw -> fw }
        )
    ) {
        content()
    }
}