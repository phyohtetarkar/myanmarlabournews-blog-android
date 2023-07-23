package com.myanmarlabournews.blog.ui.post

import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import com.myanmarlabournews.blog.ui.AppNavigationActions
import kotlinx.coroutines.delay

@Composable
fun PostDetailRoute(
    slug: String,
    viewModel: PostDetailViewModel,
    navigationActions: AppNavigationActions,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
) {
    val state by viewModel.state.collectAsState()

    val currentOnTimeout by rememberUpdatedState {
        viewModel.loadPost(slug)
    }

    LaunchedEffect(key1 = true) {
        delay(800)
        currentOnTimeout()
    }

    PostDetailScreen(
        uiState = state,
        refresh = { viewModel.loadPost(slug) },
        navigateBack = { navigationActions.navigateUp() },
        navigateToAuthor = {},
        snackbarHostState = snackbarHostState
    )

}