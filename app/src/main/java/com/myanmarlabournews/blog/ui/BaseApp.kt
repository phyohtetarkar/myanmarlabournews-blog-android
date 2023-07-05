package com.myanmarlabournews.blog.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.myanmarlabournews.blog.ui.theme.MyanmarLabourNewsTheme

@Composable
fun BaseApp() {
    MyanmarLabourNewsTheme {
        val navController = rememberNavController()


        BaseNavGraph(
            navController = navController
        )
    }
}