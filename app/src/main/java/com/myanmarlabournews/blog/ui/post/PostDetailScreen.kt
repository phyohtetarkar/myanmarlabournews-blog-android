package com.myanmarlabournews.blog.ui.post

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.myanmarlabournews.blog.R
import com.myanmarlabournews.blog.model.Post
import com.myanmarlabournews.blog.ui.search.TagChip
import com.myanmarlabournews.blog.ui.theme.MyanmarLabourNewsTheme
import com.myanmarlabournews.blog.util.timeAgo

@Composable
fun PostDetailScreen(
    post: Post,
    fillWidth: Boolean = false,
    navigateBack: () -> Unit
) {
    val context = LocalContext.current

    val modifier = if (fillWidth) {
        Modifier
    } else {
        Modifier.width(IntrinsicSize.Min)
    }
    val imageModifier = if (fillWidth) {
        Modifier
            .fillMaxWidth()
            .aspectRatio(16f / 9f)
    } else {
        Modifier
            .height(180.dp)
            .aspectRatio(16f / 9f)
    }

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
        }
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            Column() {
                ConstraintLayout() {
                    val (image, viewCount) = createRefs()

                    Box(
                        modifier = imageModifier
                            .constrainAs(image) {
                                start.linkTo(parent.start)
                                top.linkTo(parent.top)
                                end.linkTo(parent.end)
                            }
                            .fillMaxSize()
                    ) {
                        if (!post.cover.isNullOrEmpty()) {
                            AsyncImage(
                                model = post.cover,
                                contentDescription = "Cover",
                                contentScale = ContentScale.Crop,
                                modifier = imageModifier
                            )
                        } else {
                            Image(
                                painter = painterResource(id = R.drawable.cover_sample),
                                contentDescription = "Cover",
                                contentScale = ContentScale.Crop,
                                modifier = imageModifier
                            )
                        }
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .constrainAs(viewCount) {
                                end.linkTo(parent.end)
                                bottom.linkTo(image.bottom)
                            }
                            .padding(16.dp)
                            .background(
                                shape = RoundedCornerShape(corner = CornerSize(20.dp)),
                                color = Color.Black.copy(alpha = 0.3f)
                            )
                    ) {
                        Text(
                            text = post.viewCount.toString(),
                            modifier = Modifier.padding(start = 8.dp),
                            fontSize = 16.sp,
                            color = Color.White
                        )
                        Image(
                            imageVector = Icons.Default.Visibility,
                            contentDescription = "View",
                            colorFilter = ColorFilter.tint(Color.Gray),
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .size(20.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = post.title,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.profile_placeholder),
                        contentDescription = "Profile",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                    )
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
                    items(post.tags) { item ->
                        TagChip(tag = item)
                    }
                }
                Divider(thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))
            }
        }
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PostDetailScreenPreview() {
    MyanmarLabourNewsTheme {
        Surface {
            PostDetailScreen(
                post = Post.fake(),
                fillWidth = true,
                navigateBack = {}
            )
        }
    }
}
