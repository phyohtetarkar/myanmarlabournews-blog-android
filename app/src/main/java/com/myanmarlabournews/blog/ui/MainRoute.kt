package com.myanmarlabournews.blog.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.myanmarlabournews.blog.ServiceLocator
import com.myanmarlabournews.blog.model.Post
import com.myanmarlabournews.blog.util.findActivity

@Composable
fun MainRoute(
    serviceLocator: ServiceLocator,
    navigationActions: AppNavigationActions,
    locale: Post.Lang = Post.Lang.MM,
    selectedBottomNavIndex: Int = 0,
    onBottomNavChange: (index: Int) -> Unit = {},
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
) {

    val navController = rememberNavController()

    val mainNavigationActions = remember(navController) {
        MainNavigationActions(navController)
    }

    val context = LocalContext.current

    Scaffold(
        topBar = {
            BaseAppBar(
                showToggleLang = selectedBottomNavIndex == 0,
                locale = locale,
                toggleLang = { lang ->
                    (context.findActivity() as? MainActivity)?.toggleLocale(lang)
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(
                selectedIndex = selectedBottomNavIndex,
                onItemClick = { index ->
                    if (index == selectedBottomNavIndex) {
                        return@BottomNavigationBar
                    }
                    when (index) {
                        0 -> mainNavigationActions.navigateToHome()
                        1 -> mainNavigationActions.navigateToSearch()
                        2 -> mainNavigationActions.navigateToMenu()
                    }

                    onBottomNavChange(index)
                }
            )
        },
        snackbarHost = {
            AppSnackbarHost(hostState = snackbarHostState)
        }
    ) {
        Box(
            modifier = Modifier.padding(it)
        ) {
            MainNavGraph(
                serviceLocator = serviceLocator,
                navigationActions = navigationActions,
                navController = navController,
                snackbarHostState = snackbarHostState,
                locale = locale
            )
        }
    }
}