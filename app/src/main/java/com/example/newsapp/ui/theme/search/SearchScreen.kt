package com.example.newsapp.ui.theme.search

import android.annotation.SuppressLint
import android.graphics.drawable.Icon
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar

import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import com.example.newsapp.data.model.NewsUiState
import androidx.compose.material3.*
import androidx.compose.ui.unit.dp

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    componentUiState: NewsUiState,
    onValueUpdated: (String) -> Unit,
    onSearchClicked: (String) -> Unit,
    searchBarActiveUpdated: (Boolean) -> Unit

) {
    SearchBar(
        modifier = modifier
            .fillMaxWidth()
            .padding(5.dp),
        query = componentUiState.searchText,
        onQueryChange = {
            onValueUpdated(it)
        },
        onSearch = {
            onSearchClicked(it)
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
                    if (componentUiState.searchText.isNotEmpty()) onValueUpdated("")
                    else searchBarActiveUpdated(false)
                })
        }

    ) {
    }
}
