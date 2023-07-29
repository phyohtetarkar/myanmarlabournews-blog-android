package com.myanmarlabournews.blog.ui.post

import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.myanmarlabournews.blog.model.Post
import com.myanmarlabournews.blog.ui.AppNavigationActions

@Composable
fun PostsByTypeRoute(
    type: Post.Type,
    lang: Post.Lang,
    viewModel: PostsByTypeViewModel,
    navigationActions: AppNavigationActions,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
) {

    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = viewModel.launched) {
        if (!viewModel.launched) {
            viewModel.loadPosts(type, page = 0, lang)
            viewModel.launched = true
        }
    }

    PostListScreen(
        uiState = state,
        title = type.name,
        refresh = {
            viewModel.loadPosts(type, 0, lang)
        },
        paginate = {
            viewModel.loadPosts(type, state.currentPage + 1, lang, paginate = true)
        },
        navigateBack = { navigationActions.navigateUp() },
        navigateToPost = { post -> navigationActions.navigateToPost(post.slug) },
        snackbarHostState = snackbarHostState
    )

}