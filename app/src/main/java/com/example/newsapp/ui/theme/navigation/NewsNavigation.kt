package com.example.newsapp.ui.theme.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navigation
import com.example.newsapp.AppViewModelProvider
import com.example.newsapp.ui.theme.NewsTopAppBar
import com.example.newsapp.ui.theme.NewsViewModel
import com.example.newsapp.ui.theme.detail.DetailScreen
import com.example.newsapp.ui.theme.home.HomeScreen
import com.exyte.animatednavbar.AnimatedNavigationBar
import com.exyte.animatednavbar.utils.noRippleClickable

enum class NewsAppScreens(name: String) {
    HOME("Home"),
    DETAIL("Detail"),
    BOOKMARKS("Book marks"),
    SEARCH("Search")
}

enum class NavigationBarItem(val icon: ImageVector) {
    HOME(icon = Icons.Default.Home),
    SEARCH(icon = Icons.Default.Search),
    BOOKMARK(icon = Icons.Default.FavoriteBorder)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsNavigation(
    navController: NavHostController, modifier: Modifier = Modifier,
    viewModel: NewsViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val newsState = viewModel.newUiState.collectAsState()
    val newsUiState = viewModel.newsUiState.collectAsState()
    var selectedIndex by remember {
        mutableStateOf(0)
    }
    val navigationBarItems = remember {
        NavigationBarItem.values()
    }

    val backStackEntry by navController.currentBackStackEntryAsState()

    val currentScreen = NewsAppScreens.valueOf(
        backStackEntry?.destination?.route ?: NewsAppScreens.HOME.name
    )
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        topBar = {
            NewsTopAppBar(
                newScreen = currentScreen,
                scrollBehavior = scrollBehavior,
                canNavigateBack = navController.previousBackStackEntry != null,
                onNavigateUp = { navController.navigateUp() }
            )
        },
        bottomBar = {
            AnimatedNavigationBar(selectedIndex = selectedIndex) {
                navigationBarItems.forEach { item ->
                    Box(
                        modifier = Modifier
                            .height(60.dp)
                            .noRippleClickable { selectedIndex = item.ordinal },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = item.icon, contentDescription = "Bottom Bar Icon",
                            modifier = Modifier.size(26.dp),
                            tint = if (selectedIndex == item.ordinal) MaterialTheme.colorScheme.primary
                            else Color.Black
                        )
                    }
                }
            }

        }) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NewsAppScreens.HOME.name,
            modifier = modifier.padding(innerPadding)
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
}