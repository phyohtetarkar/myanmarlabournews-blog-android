package com.myanmarlabournews.blog.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Newspaper
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.myanmarlabournews.blog.ui.theme.MyanmarLabourNewsTheme

@Composable
fun BottomNavigationBar(
    selectedIndex: Int = 0,
    onItemClick: (index: Int) -> Unit,
) {
    Column {
        Divider(thickness = 0.5.dp)
        BottomNavigation(
            backgroundColor = MaterialTheme.colors.surface,
            elevation = 0.dp
        ) {

            val unselectedColor = if (MaterialTheme.colors.isLight) Color.Gray else Color.Gray

            BottomNavigationItem(
                icon = { Icon(Icons.Rounded.Newspaper, contentDescription = "Home") },
                label = { Text("Home") },
                selected = selectedIndex == 0,
                onClick = { onItemClick(0) },
                alwaysShowLabel = false,
                selectedContentColor = MaterialTheme.colors.secondary,
                unselectedContentColor = unselectedColor
            )
            BottomNavigationItem(
                icon = { Icon(Icons.Rounded.Search, contentDescription = "Search") },
                label = { Text("Search") },
                selected = selectedIndex == 1,
                onClick = { onItemClick(1) },
                alwaysShowLabel = false,
                selectedContentColor = MaterialTheme.colors.secondary,
                unselectedContentColor = unselectedColor
            )
            BottomNavigationItem(
                icon = { Icon(Icons.Rounded.Menu, contentDescription = "Menu") },
                label = { Text("Menu") },
                selected = selectedIndex == 2,
                onClick = { onItemClick(2) },
                alwaysShowLabel = false,
                selectedContentColor = MaterialTheme.colors.secondary,
                unselectedContentColor = unselectedColor
            )
        }
    }

}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun BottomNavigationBarPreview() {
    MyanmarLabourNewsTheme {
        BottomNavigationBar(
            selectedIndex = 0,
            onItemClick = {}
        )
    }
}