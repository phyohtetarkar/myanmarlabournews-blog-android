package com.myanmarlabournews.blog.ui.post

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.myanmarlabournews.blog.model.Post
import com.myanmarlabournews.blog.ui.AppSnackbarHost
import com.myanmarlabournews.blog.ui.theme.MyanmarLabourNewsTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PostListScreen(
    uiState: PostListUiState,
    title: String,
    refresh: () -> Unit,
    paginate: () -> Unit,
    navigateBack: () -> Unit,
    navigateToPost: (Post) -> Unit,
    snackbarHostState: SnackbarHostState
) {

    val state = rememberPullRefreshState(
        refreshing = uiState.isRefreshing,
        onRefresh = refresh
    )

    val coroutineScope = rememberCoroutineScope()

    val lazyColumnListState = rememberLazyListState()

    val shouldStartPaginate = remember(uiState) {
        derivedStateOf {
            if (uiState.isRefreshing || uiState.isAppending) {
                false
            } else if (uiState.totalPage <= 0) {
                false
            } else if (uiState.currentPage == uiState.totalPage - 1) {
                false
            } else {
                (lazyColumnListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                    ?: -9) >= (lazyColumnListState.layoutInfo.totalItemsCount - 3)
            }
        }
    }

    LaunchedEffect(key1 = shouldStartPaginate.value) {
        if (shouldStartPaginate.value) {
            paginate()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.primary,
                elevation = 0.dp,
                title = {
                    Text(text = title)
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            if (lazyColumnListState.canScrollBackward) {
                                coroutineScope.launch {
                                    lazyColumnListState.animateScrollToItem(0)
                                }
                            } else {
                                navigateBack()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = "Icon"
                        )
                    }
                }
            )
        },
        snackbarHost = {
            AppSnackbarHost(hostState = snackbarHostState)
        }
    ) {
        Box(
            modifier = Modifier
                .pullRefresh(state)
                .fillMaxSize()
                .padding(it),
            contentAlignment = Alignment.TopCenter
        ) {
            LazyColumn(
                state = lazyColumnListState,
                contentPadding = PaddingValues(all = 10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .wrapContentWidth()
                    .widthIn(max = 600.dp)
                    .fillMaxHeight()
            ) {
                items(uiState.posts, key = { p -> p.id }) { item ->
                    PostListItem(
                        post = item,
                        onClick = navigateToPost
                    )
                }

                if (!uiState.isRefreshing && !uiState.isAppending && uiState.posts.isEmpty()) {
                    item {
                        Text(
                            "No posts found",
                            color = Color.DarkGray,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp),
                        )
                    }
                }

                if (uiState.isAppending) {
                    item {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp),
                        ) {
                            CircularProgressIndicator(color = MaterialTheme.colors.secondary)
                        }
                    }
                }

            }

            if (uiState.refreshError != null) {
                LaunchedEffect(snackbarHostState) {
                    val result = snackbarHostState.showSnackbar(uiState.refreshError, "Retry")

                    if (result == SnackbarResult.ActionPerformed) {
                        refresh()
                    }
                }
            }

            if (uiState.paginateError != null) {
                LaunchedEffect(snackbarHostState) {
                    val result = snackbarHostState.showSnackbar(uiState.paginateError, "Retry")

                    if (result == SnackbarResult.ActionPerformed) {
                        paginate()
                    }
                }
            }

            PullRefreshIndicator(
                refreshing = uiState.isRefreshing,
                state = state,
                contentColor = MaterialTheme.colors.secondary,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PostListScreenPreview() {
    MyanmarLabourNewsTheme {
        Surface(
            color = MaterialTheme.colors.background
        ) {
            PostListScreen(
                uiState = PostListUiState(
                    posts = listOf(
                        Post.fake(1), Post.fake(2), Post.fake(3), Post.fake(4), Post.fake(5)
                    )
                ),
                title = "Posts",
                refresh = {},
                paginate = {},
                navigateBack = {},
                navigateToPost = {},
                snackbarHostState = SnackbarHostState()
            )
        }
    }
}