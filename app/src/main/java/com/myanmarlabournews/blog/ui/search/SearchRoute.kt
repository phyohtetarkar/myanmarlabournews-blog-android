package com.myanmarlabournews.blog.ui.search

import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.myanmarlabournews.blog.ui.AppNavigationActions

@Composable
fun SearchRoute(
    viewModel: SearchViewModel,
    navigationActions: AppNavigationActions,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = viewModel.launched) {
        if (!viewModel.launched) {
            viewModel.loadTags()
            viewModel.launched = true
        }
    }

    SearchScreen(
        uiState = state,
        refresh = { viewModel.loadTags() },
        navigateToTag = { tag -> navigationActions.navigateToPostsByTag(tag) },
        snackbarHostState = snackbarHostState
    )
}