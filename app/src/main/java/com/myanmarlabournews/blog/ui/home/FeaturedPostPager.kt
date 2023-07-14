package com.myanmarlabournews.blog.ui.home

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.myanmarlabournews.blog.model.Post
import com.myanmarlabournews.blog.ui.theme.MyanmarLabourNewsTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FeaturedPostPager(
    posts: List<Post>,
) {
    val pageCount = posts.size
    val pagerState = rememberPagerState(initialPage = 0)

    LaunchedEffect(key1 = posts) {
        pagerState.scrollToPage(0)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        HorizontalPager(
            pageCount = pageCount,
            state = pagerState,
            pageSpacing = 10.dp,
        ) { page ->
            FeaturedPost(
                post = posts[page],
                fillWidth = true
            )
        }
        Row {
            repeat(pageCount) { iteration ->
                val color = if (pagerState.currentPage == iteration) {
                    MaterialTheme.colors.onBackground
                } else {
                    Color.Gray
                }
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(10.dp)

                )
            }
        }
    }

}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun FeaturedPostPagerPreview() {
    MyanmarLabourNewsTheme {
        Surface {
            FeaturedPostPager(
                posts = listOf(Post.fake(), Post.fake(), Post.fake())
            )
        }

    }
}