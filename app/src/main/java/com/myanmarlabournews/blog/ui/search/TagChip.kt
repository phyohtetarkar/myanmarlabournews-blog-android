package com.myanmarlabournews.blog.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.myanmarlabournews.blog.model.Tag
import com.myanmarlabournews.blog.ui.theme.DarkCustomPrimary
import com.myanmarlabournews.blog.ui.theme.MyanmarLabourNewsTheme

@Composable
fun TagChip(
    tag: Tag,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val bgColor = if (MaterialTheme.colors.isLight) {
        Color.LightGray
    } else {
        DarkCustomPrimary
    }
    Card(
        shape = CircleShape,
        elevation = 0.dp,
        modifier = modifier
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

@Preview
@Composable
fun TagChipPreview() {
    MyanmarLabourNewsTheme {
        TagChip(
            tag = Tag.fake(),
        )
    }
}