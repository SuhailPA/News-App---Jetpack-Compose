package com.example.newsapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.newsapp.ui.theme.navigation.NavigationItem
import com.example.newsapp.ui.theme.navigation.NewsAppScreens
import kotlinx.serialization.Serializable

@Serializable
@Entity
data class NewsTable(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val title: String?,
    val url: String?,
    val urlToImage: String?
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
        urlToImage = ""
    ),
    val isShowingHomePage : Boolean = true,
    val currentNavigationItem : NavigationItem = NavigationItem.home,
    val currentScreen : NewsAppScreens = NewsAppScreens.HOME,
    val showBackButton : Boolean = false
)