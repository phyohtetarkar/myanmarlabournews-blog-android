package com.myanmarlabournews.blog.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun BaseNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = BaseDestinations.HOME_ROUTE
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(BaseDestinations.HOME_ROUTE) {

        }

        composable(BaseDestinations.SEARCH_ROUTE) {

        }

        composable(BaseDestinations.MENU_ROUTE) {

        }
    }
}