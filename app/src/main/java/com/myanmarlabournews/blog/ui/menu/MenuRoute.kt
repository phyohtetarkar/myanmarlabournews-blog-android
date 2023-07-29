package com.myanmarlabournews.blog.ui.menu

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.myanmarlabournews.blog.ui.AppNavigationActions
import com.myanmarlabournews.blog.ui.MainActivity
import com.myanmarlabournews.blog.util.findActivity

@Composable
fun MenuRoute(
    navigationActions: AppNavigationActions
) {
    val context = LocalContext.current

    MenuScreen(
        toggleDarkMode = { v ->
            (context.findActivity() as? MainActivity)?.toggleDarkMode(v)
        },
        navigateToAbout = { navigationActions.navigateToAbout() }
    )
}