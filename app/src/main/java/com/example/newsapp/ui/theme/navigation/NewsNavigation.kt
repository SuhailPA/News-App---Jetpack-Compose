package com.example.newsapp.ui.theme.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
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
import com.example.newsapp.data.model.NewsUiState
import com.example.newsapp.ui.theme.NewsTopAppBar
import com.example.newsapp.ui.theme.NewsViewModel
import com.example.newsapp.ui.theme.bookmarks.BookMarkScreen
import com.example.newsapp.ui.theme.detail.DetailScreen
import com.example.newsapp.ui.theme.home.HomeScreen
import com.example.newsapp.ui.theme.search.SearchScreen

enum class NewsAppScreens(name: String, icon: ImageVector?) {
    HOME(name = "Home", icon = Icons.Default.Home), DETAIL(name = "Detail", icon = null), BOOKMARKS(
        name = "Book marks", Icons.Default.FavoriteBorder
    ),
    SEARCH(name = "Search", icon = Icons.Default.Search)
}

enum class NavigationItem {
    home, search, bookmarks
}

data class NavigationItemContent(
    val navigationItem: NewsAppScreens, val icon: ImageVector, val text: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: NewsViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val newsState = viewModel.newUiState.collectAsState()
    val componentUiState = viewModel.newsUiState.collectAsState()
    var selectedIndex by remember {
        mutableStateOf(0)
    }
    val backStackEntry by navController.currentBackStackEntryAsState()

    val currentScreen = NewsAppScreens.valueOf(
        backStackEntry?.destination?.route ?: NewsAppScreens.HOME.name
    )
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(topBar = {
        if (navController.currentDestination?.route != NewsAppScreens.DETAIL.name) NewsTopAppBar(
            newScreen = currentScreen, scrollBehavior = scrollBehavior
        )
    }, bottomBar = {
        if (navController.currentDestination?.route != NewsAppScreens.DETAIL.name) BottomNavigationBar(
            currentScreen = currentScreen,
            onTabPressed = {
                navController.navigate(it.name) {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            })
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
                DetailScreen(newsUiState = componentUiState.value, navigateUp = {
                    navController.navigateUp()

                }, addedToFavorite = {
                    viewModel.triggeredFavorite(it)
                })
            }
            composable(route = NewsAppScreens.SEARCH.name) {
                SearchScreen(componentUiState = componentUiState.value,
                    onValueUpdated = {
                        viewModel.updateSearchTextValue(it)
                    },
                    onSearchClicked = {},
                    searchBarActiveUpdated = {
                        viewModel.updateSearchActiveButton(it)
                    })
            }
            composable(route = NewsAppScreens.BOOKMARKS.name) {
                BookMarkScreen(favouriteList = newsState.value.filter {
                    it.favourite
                }, onItemClick = {
                    viewModel.updateCurrentItem(it)
                    navController.navigate(NewsAppScreens.DETAIL.name)
                })
            }
        }
    }
}

@Composable
fun BottomNavigationBar(
    currentScreen: NewsAppScreens,
    modifier: Modifier = Modifier,
    onTabPressed: (NewsAppScreens) -> Unit,
) {
    val navigationItemContentList = listOf(
        NavigationItemContent(
            navigationItem = NewsAppScreens.HOME, icon = Icons.Default.Home, text = "Home"
        ), NavigationItemContent(
            navigationItem = NewsAppScreens.SEARCH, icon = Icons.Default.Search, text = "Search"
        ), NavigationItemContent(
            navigationItem = NewsAppScreens.BOOKMARKS,
            icon = Icons.Default.Favorite,
            text = "BookMarks"
        )
    )
    NewsBottomBar(
        navigationItemContentList = navigationItemContentList,
        onTabPressed = onTabPressed,
        currentScreen = currentScreen
    )
}

@Composable
fun NewsBottomBar(
    currentScreen: NewsAppScreens,
    modifier: Modifier = Modifier,
    navigationItemContentList: List<NavigationItemContent>,
    onTabPressed: (NewsAppScreens) -> Unit
) {
    NavigationBar(modifier = modifier) {
        navigationItemContentList.forEach { navigationItem ->
            NavigationBarItem(selected = currentScreen == navigationItem.navigationItem,
                onClick = { onTabPressed(navigationItem.navigationItem) },
                icon = {
                    Icon(
                        imageVector = navigationItem.icon, contentDescription = navigationItem.text
                    )
                })
        }
    }
}
