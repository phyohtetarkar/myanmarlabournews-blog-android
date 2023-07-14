package com.myanmarlabournews.blog.ui.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myanmarlabournews.blog.R
import com.myanmarlabournews.blog.model.Post
import com.myanmarlabournews.blog.ui.post.PostListItem
import com.myanmarlabournews.blog.ui.theme.DarkCustomPrimary
import com.myanmarlabournews.blog.ui.theme.MyanmarLabourNewsTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    uiState: HomeUiState,
    refresh: () -> Unit,
    navigateToPost: (post: Post) -> Unit,
    navigateToType: (type: Post.Type) -> Unit,
    snackbarHostState: SnackbarHostState
) {
    val state = rememberPullRefreshState(uiState.isLoading, refresh)

    Box(
        modifier = Modifier
            .pullRefresh(state)
            .fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .wrapContentWidth()
                .widthIn(max = 600.dp)
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 10.dp)
        ) {
            val size = uiState.posts.size
            val featureCount = if (size == 0) {
                0
            } else if (size > 3) {
                3
            } else {
                1
            }

            Spacer(modifier = Modifier.height(10.dp))

            if (featureCount == 3) {
//                LazyRow(
//                    horizontalArrangement = Arrangement.spacedBy(16.dp),
//                ) {
//                    items(featureCount) { index ->
//                        FeaturedPost(post = uiState.posts[index])
//                    }
//                }
                FeaturedPostPager(
                    posts = uiState.posts.subList(0, 3),
                )
            } else if (featureCount > 0) {
                FeaturedPost(post = uiState.posts[0], fillWidth = true)
            }

            if (featureCount > 0) {
                Spacer(modifier = Modifier.height(16.dp))
            }

            Text(
                text = stringResource(R.string.explore),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(12.dp))
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                items(
                    items = Post.Type.values()
                ) { postType ->
                    PostType(
                        type = postType.name,
                        onClick = {
                            navigateToType(postType)
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = stringResource(R.string.recent_posts),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(12.dp))

            if (size > featureCount) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    for (i in featureCount until size) {
                        PostListItem(post = uiState.posts[i])
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                }
            } else if (!uiState.isLoading) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = stringResource(R.string.no_posts_found),
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth(),
                )
            }

            if (uiState.errorMessage != null) {
                LaunchedEffect(snackbarHostState) {
                    val result = snackbarHostState.showSnackbar(uiState.errorMessage, "Retry")

                    if (result == SnackbarResult.ActionPerformed) {
                        refresh()
                    }
                }
            }
        }

        PullRefreshIndicator(
            refreshing = uiState.isLoading,
            state = state,
            contentColor = MaterialTheme.colors.secondary,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }

}

@Composable
fun PostType(type: String, onClick: () -> Unit = {}) {
    val bgColor = if (MaterialTheme.colors.isLight) {
        Color.LightGray
    } else {
        DarkCustomPrimary
    }
    Card(
        elevation = 0.dp,
    ) {
        Text(
            text = type,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .background(bgColor)
                .clickable(
                    onClick = onClick
                )
                .padding(horizontal = 16.dp, vertical = 12.dp)
        )
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun HomeScreenPreview() {
    MyanmarLabourNewsTheme {
        Surface(
            color = MaterialTheme.colors.background
        ) {
            HomeScreen(
                uiState = HomeUiState(
                    isLoading = false,
                    posts = listOf(Post.fake(), Post.fake(), Post.fake(), Post.fake()),
                    errorMessage = null
                ),
                refresh = {},
                navigateToPost = {},
                navigateToType = {},
                snackbarHostState = SnackbarHostState()
            )
        }

    }
}