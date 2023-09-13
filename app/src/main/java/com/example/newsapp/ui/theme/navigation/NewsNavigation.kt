package com.example.newsapp.ui.theme.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.newsapp.ui.theme.home.HomeScreen

enum class NewsAppScreens {
    HOME, DETAIL, BOOKMARKS, SEARCH
}

@Composable
fun NewsNavigation(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = NewsAppScreens.HOME.name,
        modifier = modifier
    ) {
        composable(route = NewsAppScreens.HOME.name) {
            HomeScreen()
        }
    }
}