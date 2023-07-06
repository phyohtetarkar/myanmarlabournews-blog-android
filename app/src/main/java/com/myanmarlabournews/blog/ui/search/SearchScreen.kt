package com.myanmarlabournews.blog.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.myanmarlabournews.blog.ui.BaseAppBar
import com.myanmarlabournews.blog.ui.BottomNavigationBar
import com.myanmarlabournews.blog.ui.theme.MyanmarLabourNewsTheme

@Composable
fun SearchScreen() {
    Scaffold(
        topBar = {
            BaseAppBar()
        },
        bottomBar = { BottomNavigationBar(selectedIndex = 1) },

        ) {
        Box() {
            LazyColumn(
                modifier = Modifier.padding(it),
            ) {
                items(5) {
                    TagRow()
                    Divider()
                }
            }
        }
    }
}

@Composable
fun TagRow(
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .clickable(onClick = onClick)
            .background(MaterialTheme.colors.surface)
            .padding(16.dp)
    ) {
        Text(text = "Tag name", modifier = Modifier.weight(1f))
        Icon(imageVector = Icons.Rounded.ChevronRight, contentDescription = "Icon")
    }
}

@Preview
@Composable
fun SearchScreenPreview() {
    MyanmarLabourNewsTheme {
        SearchScreen()
    }
}