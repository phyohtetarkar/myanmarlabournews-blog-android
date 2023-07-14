package com.myanmarlabournews.blog.ui.search

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.myanmarlabournews.blog.model.Tag
import com.myanmarlabournews.blog.ui.theme.DarkCustomPrimary
import com.myanmarlabournews.blog.ui.theme.MyanmarLabourNewsTheme

@OptIn(ExperimentalMaterialApi::class, ExperimentalLayoutApi::class)
@Composable
fun SearchScreen(
    uiState: SearchUiState,
    refresh: () -> Unit,
    navigateToTag: (tag: Tag) -> Unit,
    snackbarHostState: SnackbarHostState
) {
    val state = rememberPullRefreshState(uiState.isLoading, refresh)

    BoxWithConstraints(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .pullRefresh(state)
            .fillMaxSize()
    ) {
        if (uiState.tags.isNotEmpty()) {
            FlowRow(
                modifier = Modifier
                    .wrapContentWidth()
                    .widthIn(max = 600.dp)
                    .fillMaxSize()
                    .padding(10.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                val tags = uiState.tags
                for (tag in tags) {
                    TagChip(tag = tag)
                }
            }
        }

        if (uiState.errorMessage != null) {
            LaunchedEffect(snackbarHostState) {
                val result = snackbarHostState.showSnackbar(uiState.errorMessage, "Retry")

                if (result == SnackbarResult.ActionPerformed) {
                    refresh()
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
fun TagChip(
    tag: Tag,
    onClick: () -> Unit = {}
) {
    val bgColor = if (MaterialTheme.colors.isLight) {
        Color.LightGray
    } else {
        DarkCustomPrimary
    }
    Card(
        shape = CircleShape,
        elevation = 0.dp,
        modifier = Modifier
            .padding(bottom = 10.dp)
    ) {
        Text(
            text = tag.name,
            modifier = Modifier
                .background(bgColor)
                .clickable(onClick = onClick)
                .padding(horizontal = 14.dp, vertical = 8.dp)
        )
    }
}

@Composable
fun TagListItem(
    tag: Tag,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .clickable(onClick = onClick)
            .background(MaterialTheme.colors.surface)
            .padding(16.dp)
    ) {
        Text(
            text = tag.name,
            modifier = Modifier.weight(1f)
        )
        Icon(
            imageVector = Icons.Rounded.ChevronRight,
            contentDescription = "Icon",
            tint = Color.Gray
        )
    }
}

@Composable
fun TagGridItem(
    tag: Tag,
    onClick: () -> Unit = {}
) {
    Card(
        shape = RoundedCornerShape(2.dp),
        modifier = Modifier
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .background(MaterialTheme.colors.surface)
                .padding(16.dp)
        ) {
            Text(
                text = tag.name,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.Rounded.ChevronRight,
                contentDescription = "Icon",
                tint = Color.Gray
            )
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SearchScreenPreview() {
    MyanmarLabourNewsTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            SearchScreen(
                uiState = SearchUiState(
                    isLoading = false,
                    tags = listOf(Tag.fake(), Tag.fake()),
                    errorMessage = null
                ),
                refresh = {},
                navigateToTag = {},
                snackbarHostState = SnackbarHostState()
            )
        }
    }
}