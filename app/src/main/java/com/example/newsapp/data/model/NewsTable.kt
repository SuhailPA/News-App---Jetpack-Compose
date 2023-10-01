package com.example.newsapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.newsapp.ui.navigation.NavigationItem
import com.example.newsapp.ui.navigation.NewsAppScreens
import kotlinx.serialization.Serializable

@Serializable
@Entity
data class NewsTable(
    val id: Int = 0,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    @PrimaryKey
    val title: String,
    val url: String?,
    val urlToImage: String?,
    var favourite: Boolean = false
)

data class NewsUiState(
    val selectedItem: NewsTable = NewsTable(
        id = 0,
        author = "",
        content = "",
        description = "",
        publishedAt = "",
        title = "",
        url = "",
        urlToImage = "",
        favourite = false
    ),
    val isShowingHomePage: Boolean = true,
    val currentNavigationItem: NavigationItem = NavigationItem.home,
    val currentScreen: NewsAppScreens = NewsAppScreens.HOME,
    val showBackButton: Boolean = false,
    val searchText: String = "",
    val searchBarIsActive: Boolean = false,
    val historyItems : List<HistoryTable> = listOf(HistoryTable("")),
    val searchItems : List<NewsTable> = listOf()
)

@Entity
data class HistoryTable(
    @PrimaryKey
    val historyItem: String
)