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
fun PostsByTagRoute(
    tagId: Int,
    tagName: String,
    lang: Post.Lang,
    viewModel: PostsByTagViewModel,
    navigationActions: AppNavigationActions,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.loadPosts(tagId, page = 0, lang)
    }

    PostListScreen(
        uiState = state,
        title = tagName,
        refresh = {
            viewModel.loadPosts(tagId, 0, lang)
        },
        paginate = {
            viewModel.loadPosts(tagId, state.currentPage + 1, lang, paginate = true)
        },
        navigateBack = { navigationActions.navigateUp() },
        navigateToPost = { post -> navigationActions.navigateToPost(post.slug) },
        snackbarHostState = snackbarHostState
    )
}