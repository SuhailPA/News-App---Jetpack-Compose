package com.example.newsapp.ui.bookmarks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.newsapp.data.model.NewsTable
import com.example.newsapp.ui.home.NewsItem

@Composable
fun BookMarkScreen(
    modifier: Modifier = Modifier,
    favouriteList: List<NewsTable?>,
    onItemClick: (NewsTable) -> Unit
) {
    if (favouriteList.isEmpty()) {
        EmptyFavouriteList()
    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize(), contentPadding = PaddingValues(5.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(items = favouriteList, key = { item -> item?.id!! }) {
                if (it != null) {
                    NewsItem(news = it, onItemClick = { onItemClick(it) })
                }
            }
        }
    }

}

@Composable
fun EmptyFavouriteList(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "The Favourite list is Empty",
            style = MaterialTheme.typography.titleLarge
        )
    }
}