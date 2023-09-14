package com.example.newsapp.ui.theme

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.ui.theme.navigation.NewsAppScreens
import com.example.newsapp.ui.theme.navigation.NewsNavigation


@Composable
fun NewsApp(navController: NavHostController = rememberNavController()) {
    NewsNavigation(navController = navController)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsTopAppBar(
    modifier: Modifier = Modifier,
    newScreen: NewsAppScreens,
    canNavigateBack: Boolean,
    scrollBehavior: TopAppBarScrollBehavior,
    onNavigateUp: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = newScreen.name,
                style = MaterialTheme.typography.titleLarge
            )
        },
        scrollBehavior = scrollBehavior,
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = { onNavigateUp() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack, contentDescription = "Back"
                    )
                }
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}
