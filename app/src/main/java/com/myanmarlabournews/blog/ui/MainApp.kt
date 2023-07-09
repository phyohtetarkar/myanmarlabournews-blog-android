package com.myanmarlabournews.blog.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.myanmarlabournews.blog.ui.theme.MyanmarLabourNewsTheme

@Composable
fun MainApp() {
    MyanmarLabourNewsTheme {
        val navController = rememberNavController()


        AppNavGraph(
            navController = navController
        )
    }
}