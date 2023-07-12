package com.myanmarlabournews.blog.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.myanmarlabournews.blog.ServiceLocator
import com.myanmarlabournews.blog.ui.theme.MyanmarLabourNewsTheme

@Composable
fun MainApp(
    mainViewModel: MainViewModel,
    serviceLocator: ServiceLocator,
) {
    val darkMode by mainViewModel.darkMode.collectAsState()

    val locale by mainViewModel.lang.collectAsState()

    MyanmarLabourNewsTheme(
        darkTheme = darkMode
    ) {
        val navController = rememberNavController()
        val navigationActions = remember(navController) {
            AppNavigationActions(navController)
        }

        AppNavGraph(
            navController = navController,
            serviceLocator = serviceLocator,
            navigationActions = navigationActions,
            locale = locale
        )
    }
}
