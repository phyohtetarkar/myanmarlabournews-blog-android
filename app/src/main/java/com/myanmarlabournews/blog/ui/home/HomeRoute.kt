package com.myanmarlabournews.blog.ui.home

import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.myanmarlabournews.blog.model.Post
import com.myanmarlabournews.blog.ui.AppNavigationActions

@Composable
fun HomeRoute(
    viewModel: HomeViewModel,
    navigationActions: AppNavigationActions,
    locale: Post.Lang = Post.Lang.MM,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = viewModel.launched, key2 = locale) {
        if (!viewModel.launched || locale != viewModel.locale) {
            viewModel.loadPosts(locale)
            viewModel.launched = true
            viewModel.locale = locale
        }
    }

    HomeScreen(
        uiState = state,
        refresh = {
            viewModel.loadPosts(locale)
        },
        snackbarHostState = snackbarHostState,
        navigateToPost = { post ->
            navigationActions.navigateToPost(post.slug)
        },
        navigateToType = {

        },
    )
}