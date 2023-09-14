package com.example.newsapp.ui.theme.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.newsapp.AppViewModelProvider
import com.example.newsapp.ui.theme.NewsViewModel
import com.example.newsapp.ui.theme.detail.DetailScreen
import com.example.newsapp.ui.theme.home.HomeScreen

enum class NewsAppScreens {
    HOME, DETAIL, BOOKMARKS, SEARCH
}

@Composable
fun NewsNavigation(
    navController: NavHostController, modifier: Modifier = Modifier,
    viewModel: NewsViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val newsState = viewModel.newUiState.collectAsState()
    val newsUiState = viewModel.newsUiState.collectAsState()
    NavHost(
        navController = navController,
        startDestination = NewsAppScreens.HOME.name,
        modifier = modifier
    ) {
        composable(route = NewsAppScreens.HOME.name) {
            HomeScreen(newsData = newsState.value, onItemClick = {
                viewModel.updateCurrentItem(newsTable = it)
                navController.navigate(route = NewsAppScreens.DETAIL.name)
            })
        }

        composable(route = NewsAppScreens.DETAIL.name) {
            DetailScreen(newsItem = newsUiState.value.selectedItem, navigateUp = {
                navController.navigateUp()
            })
        }
    }
}