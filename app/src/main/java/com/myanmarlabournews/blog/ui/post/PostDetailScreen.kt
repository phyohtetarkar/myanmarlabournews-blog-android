package com.myanmarlabournews.blog.ui.post

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
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
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewStateWithHTMLData
import com.myanmarlabournews.blog.R
import com.myanmarlabournews.blog.model.Author
import com.myanmarlabournews.blog.model.Post
import com.myanmarlabournews.blog.model.Tag
import com.myanmarlabournews.blog.ui.AppSnackbarHost
import com.myanmarlabournews.blog.ui.search.TagChip
import com.myanmarlabournews.blog.ui.theme.MyanmarLabourNewsTheme
import com.myanmarlabournews.blog.util.compactFormat
import com.myanmarlabournews.blog.util.timeAgo
import com.myanmarlabournews.blog.util.wrapWithHtml

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PostDetailScreen(
    uiState: PostDetailUiState,
    refresh: () -> Unit,
    navigateBack: () -> Unit,
    navigateToAuthor: (Author) -> Unit,
    navigateToTag: (Tag) -> Unit,
    snackbarHostState: SnackbarHostState
) {
    val state = rememberPullRefreshState(refreshing = uiState.isLoading, onRefresh = refresh)

    val imageModifier = Modifier
        .fillMaxWidth()

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.primary,
                elevation = 0.dp,
                title = {},
                navigationIcon = {
                    IconButton(
                        onClick = navigateBack
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
                .background(MaterialTheme.colors.surface)
                .padding(it)
                .fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier
                    .wrapContentWidth()
                    .widthIn(max = 600.dp)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                if (uiState.post != null) {
                    val post = uiState.post
                    ConstraintLayout {
                        val (image, viewCount) = createRefs()

                        Box(
                            modifier = imageModifier
                                .constrainAs(image) {
                                    start.linkTo(parent.start)
                                    top.linkTo(parent.top)
                                    end.linkTo(parent.end)
                                }
                        ) {
                            if (!post.cover.isNullOrEmpty()) {
                                AsyncImage(
                                    model = post.cover,
                                    contentDescription = "Cover",
                                    contentScale = ContentScale.FillWidth,
                                    modifier = imageModifier
                                )
                            } else {
                                Image(
                                    painter = painterResource(id = R.drawable.placeholder),
                                    contentDescription = "Cover",
                                    contentScale = ContentScale.FillWidth,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .aspectRatio(16f / 9f)
                                )
                            }
                        }
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .constrainAs(viewCount) {
                                    end.linkTo(parent.end, margin = 16.dp)
                                    bottom.linkTo(image.bottom, margin = 16.dp)
                                }
                                .background(
                                    shape = RoundedCornerShape(corner = CornerSize(20.dp)),
                                    color = Color.Black.copy(alpha = 0.7f)
                                )
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = post.viewCount?.compactFormat() ?: "0",
                                color = Color.LightGray
                            )
                            Icon(
                                imageVector = Icons.Default.Visibility,
                                contentDescription = "View",
                                tint = Color.LightGray,
                                modifier = Modifier
                                    .size(20.dp),
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = post.title,
                        modifier = Modifier.padding(horizontal = 16.dp),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .clickable {
                                navigateToAuthor(post.author)
                            }
                            .padding(horizontal = 16.dp)
                    ) {
                        if (post.author.image.isNullOrEmpty()) {
                            Image(
                                painter = painterResource(id = R.drawable.profile_placeholder),
                                contentDescription = "Profile",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(56.dp)
                                    .clip(CircleShape)
                            )
                        } else {
                            AsyncImage(
                                model = post.author.image,
                                contentDescription = "Profile",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(56.dp)
                                    .clip(CircleShape)
                            )
                        }

                        Column {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                            ) {
                                Text(
                                    text = stringResource(id = R.string.by),
                                    color = Color.Gray
                                )
                                Text(
                                    text = post.author.name,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Text(
                                text = post.publishedAt?.timeAgo() ?: post.createdAt.timeAgo(),
                                color = Color.Gray
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    LazyRow(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        items(post.tags) { tag ->
                            TagChip(
                                tag = tag,
                                onClick = { navigateToTag(tag) }
                            )
                        }
                    }
                    Divider(thickness = 1.dp, modifier = Modifier.padding(top = 16.dp))

                    ComposeWebView(
                        content = post.body?.wrapWithHtml(MaterialTheme.colors.isLight) ?: ""
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
}

@Composable
fun ComposeWebView(
    content: String
) {
    val state = rememberWebViewStateWithHTMLData(
        data = content,
        encoding = "utf-8",
        mimeType = "text/html"
    )

    WebView(
        state,
        onCreated = {
            it.settings.javaScriptEnabled = true
            it.isVerticalScrollBarEnabled = false
            it.isHorizontalScrollBarEnabled = false
        },
        modifier = Modifier
            .padding(horizontal = 12.dp)
    )
//    AndroidView(
//        factory = {
//            WebView(it).apply {
//                webChromeClient = WebChromeClient()
//                settings.javaScriptEnabled = true
//                isVerticalScrollBarEnabled = false
//                isHorizontalScrollBarEnabled = false
//                alpha = 0.5f
//
//            }
//        },
//        update = {
//            it.loadDataWithBaseURL(null, content, "text/html", "utf-8", null)
//        },
//        modifier = Modifier
//            .padding(horizontal = 12.dp)
//    )
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PostDetailScreenPreview() {
    MyanmarLabourNewsTheme {
        Surface {
            PostDetailScreen(
                uiState = PostDetailUiState(isLoading = false, post = Post.fake()),
                refresh = {},
                navigateBack = {},
                navigateToAuthor = {},
                navigateToTag = {},
                snackbarHostState = SnackbarHostState()
            )
        }
    }
}
