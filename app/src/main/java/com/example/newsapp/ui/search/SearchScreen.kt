package com.example.newsapp.ui.search

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon

import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import com.example.newsapp.data.model.NewsUiState
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newsapp.data.model.HistoryTable
import com.example.newsapp.ui.home.NewsItem

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    historyItems: List<HistoryTable>,
    componentUiState: NewsUiState,
    onValueUpdated: (String) -> Unit,
    onSearchClicked: (String) -> Unit,
    onClearTrailItems: () -> Unit,
    searchBarActiveUpdated: (Boolean) -> Unit

) {
    Column(modifier = modifier.fillMaxSize()) {
        DockedSearchBar(
            modifier = modifier
                .fillMaxWidth()
                .padding(5.dp),
            query = componentUiState.searchText,
            onQueryChange = {
                onValueUpdated(it)
            },
            onSearch = {
                onSearchClicked(it)
                searchBarActiveUpdated(false)
            },
            active = componentUiState.searchBarIsActive,
            onActiveChange = {
                searchBarActiveUpdated(it)
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
            },
            trailingIcon = {
                Icon(imageVector = Icons.Default.Close, contentDescription = "Close",
                    modifier = Modifier.clickable {
                        if (componentUiState.searchText.isNotEmpty()) {
                            onValueUpdated("")
                        } else {
                            searchBarActiveUpdated(false)
                        }
                        onClearTrailItems()
                    })
            },
            placeholder = { Text(text = "Search Bar")}
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(shape = RoundedCornerShape(15.dp)),
            ) {
                items(items = historyItems, key = { item -> item.historyItem }) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                            .clickable {
                                onValueUpdated(it.historyItem)
                            }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "History",
                            modifier = Modifier.padding(5.dp)
                        )
                        Text(
                            text = it.historyItem,
                            fontSize = 15.sp,
                            modifier = Modifier.padding(start = 15.dp, top = 5.dp)
                        )
                    }

                }
            }
        }
        LazyColumn(
            modifier = Modifier.padding(15.dp),
            contentPadding = PaddingValues(5.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(componentUiState.searchItems) {
                NewsItem(news = it, onItemClick = {})
            }
        }

    }
}

@Composable
fun EmptySearchResult(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "No Search Result found", style = MaterialTheme.typography.titleLarge)
    }
}
