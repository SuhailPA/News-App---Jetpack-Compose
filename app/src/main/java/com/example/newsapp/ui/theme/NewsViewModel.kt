package com.example.newsapp.ui.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.model.Article
import com.example.newsapp.data.model.NewsResponseModel
import com.example.newsapp.data.model.NewsTable
import com.example.newsapp.data.model.NewsUiState
import com.example.newsapp.data.repository.NewsRepository
import com.example.newsapp.ui.theme.navigation.NavigationItem
import com.example.newsapp.ui.theme.navigation.NewsAppScreens
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewsViewModel(private val repository: NewsRepository) : ViewModel() {

    private val _newsUiState = MutableStateFlow(NewsUiState())
    val newsUiState: StateFlow<NewsUiState> = _newsUiState

    init {
        viewModelScope.launch {
            repository.updateNewsContent()
        }
    }

    val newUiState: StateFlow<List<NewsTable>> = repository.getAllNews().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = listOf<NewsTable>()
    )

    fun updateCurrentItem(newsTable: NewsTable) {
        _newsUiState.update { currentState ->
            currentState.copy(
                selectedItem = newsTable
            )
        }
    }

    fun updateCurrentScreen(currentScreen: NewsAppScreens) {
        _newsUiState.update { currentState ->
            currentState.copy(
                currentScreen = currentScreen
            )
        }
    }

    fun updateBackButton(value: Boolean) {
        _newsUiState.update { currentState ->
            currentState.copy(
                showBackButton = value
            )

        }
    }
}