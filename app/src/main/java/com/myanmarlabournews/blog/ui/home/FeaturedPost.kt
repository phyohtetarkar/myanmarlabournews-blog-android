package com.myanmarlabournews.blog.ui.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
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
        elevation = 0.dp
    ) {
        ConstraintLayout(
            modifier = modifier
                .clickable {
                    onClick?.invoke(post)
                }
        ) {

            val (image, type, title, date) = createRefs()

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
                        contentDescription = "Cove",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.cover_sample),
                        contentDescription = "Cover",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.3f))
                )
            }

            Text(
                text = post.type.name,
                fontSize = 14.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .constrainAs(type) {
                        start.linkTo(parent.start, margin = 16.dp)
                        bottom.linkTo(title.top, margin = 8.dp)
                    }
                    .border(
                        width = 1.dp,
                        color = Color.LightGray,
                        shape = RoundedCornerShape(2.dp)
                    )
                    .padding(horizontal = 6.dp, vertical = 4.dp)
            )

            Text(
                text = post.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.White,
                modifier = Modifier
                    .constrainAs(title) {
                        start.linkTo(parent.start, margin = 16.dp)
                        end.linkTo(parent.end, margin = 16.dp)
                        bottom.linkTo(date.top, margin = 4.dp)
                        width = Dimension.fillToConstraints
                    }
            )
            Text(
                text = post.publishedAt?.timeAgo() ?: post.createdAt.timeAgo(),
                color = Color.LightGray,
                modifier = Modifier
                    .constrainAs(date) {
                        start.linkTo(parent.start, margin = 16.dp)
                        bottom.linkTo(parent.bottom, margin = 16.dp)
                    }
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