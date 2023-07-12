package com.myanmarlabournews.blog.ui

import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.myanmarlabournews.blog.ServiceLocator
import com.myanmarlabournews.blog.model.Post
import com.myanmarlabournews.blog.ui.home.HomeRoute
import com.myanmarlabournews.blog.ui.home.HomeViewModel
import com.myanmarlabournews.blog.ui.menu.MenuRoute
import com.myanmarlabournews.blog.ui.search.SearchRoute
import com.myanmarlabournews.blog.ui.search.SearchViewModel

@Composable
fun MainNavGraph(
    serviceLocator: ServiceLocator,
    navigationActions: AppNavigationActions,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    locale: Post.Lang = Post.Lang.MM,
    navController: NavHostController = rememberNavController(),
    startDestination: String = AppDestination.Main.Home.route
) {

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(AppDestination.Main.Home.route) {
            val homeViewModel = viewModel<HomeViewModel>(
                factory = HomeViewModel.provideFactory(
                    serviceLocator.postRepo,
                )
            )
            HomeRoute(
                viewModel = homeViewModel,
                navigationActions = navigationActions,
                snackbarHostState = snackbarHostState,
                locale = locale
            )
        }

        composable(AppDestination.Main.Search.route) {
            val searchViewModel = viewModel<SearchViewModel>(
                factory = SearchViewModel.provideFactory(serviceLocator.tagRepo)
            )
            SearchRoute(
                viewModel = searchViewModel,
                navigationActions = navigationActions,
                snackbarHostState = snackbarHostState
            )
        }

        composable(AppDestination.Main.Menu.route) {
            MenuRoute(navigationActions = navigationActions)
        }
    }
}