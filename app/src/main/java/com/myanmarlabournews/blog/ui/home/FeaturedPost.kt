package com.myanmarlabournews.blog.ui.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.myanmarlabournews.blog.R
import com.myanmarlabournews.blog.model.Post
import com.myanmarlabournews.blog.ui.theme.MyanmarLabourNewsTheme
import com.myanmarlabournews.blog.util.timeAgo

@Composable
fun FeaturedPost(
    post: Post,
    fillWidth: Boolean = false,
    onClick: ((post: Post) -> Unit)? = null
) {

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

    Card(
        shape = RoundedCornerShape(4.dp),
    ) {
        Column(
            modifier = modifier
                .clickable {
                    onClick?.invoke(post)
                }
                .padding(bottom = 16.dp)
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
                    painter = painterResource(id = R.drawable.placeholder),
                    contentDescription = "Cover",
                    contentScale = ContentScale.Crop,
                    modifier = imageModifier
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = post.type.name,
                    color = Color.Gray,
                )
                Text(
                    text = post.publishedAt?.timeAgo() ?: post.createdAt.timeAgo(),
                    color = Color.Gray
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = post.title,
                modifier = Modifier.padding(horizontal = 16.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )


        }
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun FeaturedPostPreview() {
    MyanmarLabourNewsTheme {
        FeaturedPost(
            post = Post.fake(),
            fillWidth = true
        )
    }
}