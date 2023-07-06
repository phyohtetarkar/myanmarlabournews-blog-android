package com.myanmarlabournews.blog.ui.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.myanmarlabournews.blog.R
import com.myanmarlabournews.blog.ui.theme.MyanmarLabourNewsTheme
import com.myanmarlabournews.blog.util.timeAgo

@Composable
fun FeaturedPost() {
    Card(
        modifier = Modifier.clickable { }
    ) {
        Column(
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .padding(bottom = 16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.cover_sample),
                contentDescription = "Cover",
                modifier = Modifier
                    .height(180.dp)
                    .aspectRatio(16f / 9f),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Post title",
                modifier = Modifier.padding(horizontal = 16.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = 1688614712249.timeAgo(),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                color = Color.Gray
            )
        }
    }
}

@Preview
@Composable
fun FeaturedPostPreview() {
    MyanmarLabourNewsTheme {
        FeaturedPost()
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun FeaturedPostPreviewNight() {
    MyanmarLabourNewsTheme {
        FeaturedPost()
    }
}