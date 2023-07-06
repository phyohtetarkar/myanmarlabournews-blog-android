package com.myanmarlabournews.blog.ui.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myanmarlabournews.blog.R
import com.myanmarlabournews.blog.ui.theme.MyanmarLabourNewsTheme
import com.myanmarlabournews.blog.util.timeAgo

@Composable
fun RecentPost() {
    Card(
        shape = RoundedCornerShape(2.dp),
        modifier = Modifier.clickable { },
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Article",
                    color = Color.Gray,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "Post title",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = 1688614712249.timeAgo(),
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Image(
                painter = painterResource(id = R.drawable.cover_sample),
                contentDescription = "Cover",
                modifier = Modifier
                    .height(80.dp)
                    .aspectRatio(4f / 3f)
                    .clip(RoundedCornerShape(4.dp)),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Preview
@Composable
fun RecentPostPreview() {
    MyanmarLabourNewsTheme {
        RecentPost()
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun RecentPostPreviewNight() {
    MyanmarLabourNewsTheme {
        RecentPost()
    }
}