package com.myanmarlabournews.blog.ui.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myanmarlabournews.blog.R
import com.myanmarlabournews.blog.ui.BaseAppBar
import com.myanmarlabournews.blog.ui.BottomNavigationBar
import com.myanmarlabournews.blog.ui.theme.DarkCustomPrimary
import com.myanmarlabournews.blog.ui.theme.MyanmarLabourNewsTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen() {
    var refreshing by remember { mutableStateOf(false) }

    val state = rememberPullRefreshState(refreshing, {})

    Scaffold(
        topBar = {
            BaseAppBar()
        },
        bottomBar = { BottomNavigationBar() }
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .pullRefresh(state)
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    items(3) {
                        FeaturedPost()
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = stringResource(R.string.explore),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.horizontalScroll(rememberScrollState())
                ) {
                    PostType(type = "News")
                    PostType(type = "Articles")
                    PostType(type = "Videos")
                    PostType(type = "Podcasts")
                    PostType(type = "Surveys")
                    PostType(type = "Laws")
                }
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = stringResource(R.string.recent_posts),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.height(12.dp))
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    for (i in 0..5) {
                        RecentPost()
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }

            }

            PullRefreshIndicator(
                refreshing = refreshing,
                state = state,
                contentColor = MaterialTheme.colors.secondary,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }

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
        backgroundColor = bgColor,
        elevation = 0.dp,
        modifier = Modifier.clickable(
            onClick = onClick
        )
    ) {
        Text(
            text = type,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
        )
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    MyanmarLabourNewsTheme {
        HomeScreen()
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun HomeScreenPreviewNight() {
    MyanmarLabourNewsTheme {
        HomeScreen()
    }
}