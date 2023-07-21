package com.myanmarlabournews.blog.ui.post

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.myanmarlabournews.blog.model.Post
import com.myanmarlabournews.blog.ui.theme.MyanmarLabourNewsTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PostListScreen(
    refresh: () -> Unit,
    posts: List<Post>
) {
    val state = rememberPullRefreshState(refreshing = false, onRefresh = refresh)

    Box(
        modifier = Modifier
            .pullRefresh(state)
            .fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        LazyColumn(
            contentPadding = PaddingValues(all = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(posts) { item ->
                PostListItem(post = item)
            }
        }

        PullRefreshIndicator(
            refreshing = false,
            state = state,
            contentColor = MaterialTheme.colors.secondary,
            modifier = Modifier.align(Alignment.TopCenter)
        )
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
                refresh = { },
                posts = listOf(
                    Post.fake(), Post.fake(), Post.fake(), Post.fake(), Post.fake()
                )
            )
        }
    }
}