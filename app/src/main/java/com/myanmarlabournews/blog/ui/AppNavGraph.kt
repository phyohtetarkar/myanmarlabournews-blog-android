package com.myanmarlabournews.blog.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.myanmarlabournews.blog.ServiceLocator
import com.myanmarlabournews.blog.model.Post
import com.myanmarlabournews.blog.ui.about.AboutScreen
import com.myanmarlabournews.blog.ui.post.PostDetailRoute
import com.myanmarlabournews.blog.ui.post.PostDetailViewModel
import com.myanmarlabournews.blog.ui.post.PostsByAuthorRoute
import com.myanmarlabournews.blog.ui.post.PostsByAuthorViewModel
import com.myanmarlabournews.blog.ui.post.PostsByTagRoute
import com.myanmarlabournews.blog.ui.post.PostsByTagViewModel
import com.myanmarlabournews.blog.ui.post.PostsByTypeRoute
import com.myanmarlabournews.blog.ui.post.PostsByTypeViewModel
import com.myanmarlabournews.blog.ui.splash.SplashScreen

@Composable
fun AppNavGraph(
    serviceLocator: ServiceLocator,
    navigationActions: AppNavigationActions,
    modifier: Modifier = Modifier,
    locale: Post.Lang = Post.Lang.MM,
    navController: NavHostController = rememberNavController(),
    startDestination: String = AppDestination.Main.route
) {
    var selectedItemIndex by remember { mutableStateOf(0) }

    val uri = "https://www.myanmarlabournews.com"

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {

        composable(AppDestination.Splash.route) {
            SplashScreen(
                onTimeout = {
                    navigationActions.navigateToMain()
                }
            )
        }

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

        composable(
            AppDestination.Post.route,
            arguments = listOf(navArgument("slug") { type = NavType.StringType }),
            deepLinks = listOf(
                navDeepLink { uriPattern = "$uri/posts/{slug}" },
                navDeepLink { uriPattern = "$uri/en/posts/{slug}" }
            )
        ) { backStackEntry ->
            val viewModel = viewModel<PostDetailViewModel>(
                factory = PostDetailViewModel.provideFactory(
                    serviceLocator.postRepo,
                )
            )

            PostDetailRoute(
                slug = backStackEntry.arguments?.getString("slug") ?: "",
                viewModel = viewModel,
                navigationActions = navigationActions
            )
        }

        composable(
            AppDestination.PostsByType.route,
            arguments = listOf(navArgument("type") { type = NavType.StringType }),
        ) { backStackEntry ->
            val viewModel = viewModel<PostsByTypeViewModel>(
                factory = PostsByTypeViewModel.provideFactory(
                    serviceLocator.postRepo,
                )
            )

            val type = backStackEntry.arguments?.getString("type") ?: "NEWS"

            PostsByTypeRoute(
                type = Post.Type.valueOf(type),
                lang = locale,
                viewModel = viewModel,
                navigationActions = navigationActions
            )
        }

        composable(
            AppDestination.PostsByTag.route,
            arguments = listOf(
                navArgument("tagId") { type = NavType.IntType },
                navArgument("tagName") { type = NavType.StringType }
            ),
        ) { backStackEntry ->
            val viewModel = viewModel<PostsByTagViewModel>(
                factory = PostsByTagViewModel.provideFactory(
                    serviceLocator.tagRepo,
                )
            )

            val tagId = backStackEntry.arguments?.getInt("tagId") ?: 0
            val tagName = backStackEntry.arguments?.getString("tagName") ?: ""

            PostsByTagRoute(
                tagId = tagId,
                tagName = tagName,
                lang = locale,
                viewModel = viewModel,
                navigationActions = navigationActions
            )
        }

        composable(
            AppDestination.PostsByAuthor.route,
            arguments = listOf(
                navArgument("authorId") { type = NavType.LongType },
                navArgument("authorName") { type = NavType.StringType }
            ),
        ) { backStackEntry ->
            val viewModel = viewModel<PostsByAuthorViewModel>(
                factory = PostsByAuthorViewModel.provideFactory(
                    serviceLocator.authorRepo,
                )
            )

            val authorId = backStackEntry.arguments?.getLong("authorId") ?: 0
            val authorName = backStackEntry.arguments?.getString("authorName") ?: ""

            PostsByAuthorRoute(
                authorId = authorId,
                authorName = authorName,
                lang = locale,
                viewModel = viewModel,
                navigationActions = navigationActions
            )
        }
    }
}

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

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun FadeThroughAnimation(
    content: @Composable () -> Unit
) {
    val visibleState = MutableTransitionState(false).apply {
        // Start the animation immediately.
        targetState = true
    }
    AnimatedVisibility(
        visibleState = visibleState,
        enter = fadeIn() + scaleIn(
            initialScale = 0.95f,
        ),
        exit = fadeOut()
    ) {
        content()
    }
}