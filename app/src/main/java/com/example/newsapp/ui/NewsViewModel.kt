package com.example.newsapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.model.HistoryTable
import com.example.newsapp.data.model.NewsTable
import com.example.newsapp.data.model.NewsUiState
import com.example.newsapp.data.repository.NewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewsViewModel(private val repository: NewsRepository) : ViewModel() {

    private val _newsUiState = MutableStateFlow(NewsUiState())
    val newsUiState: StateFlow<NewsUiState> = _newsUiState

    private val _yourStateFlow = MutableStateFlow<List<NewsTable>>(emptyList())
    val yourStateFlow: StateFlow<List<NewsTable>> = _yourStateFlow.asStateFlow()

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

    val historyItems: StateFlow<List<HistoryTable>> = repository.getAllHistoryItems().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = listOf()
    )

    fun updateCurrentItem(newsTable: NewsTable) {
        _newsUiState.update { currentState ->
            currentState.copy(
                selectedItem = newsTable
            )
        }
    }

    fun triggeredFavorite(favourite: Boolean) {
        viewModelScope.launch {
            _newsUiState.update { currentState ->
                currentState.copy(
                    selectedItem = newsUiState.value.selectedItem.copy(
                        favourite = favourite
                    )
                )
            }
            repository.triggeredFavorite(_newsUiState.value.selectedItem)
        }
    }

    fun updateSearchTextValue(text: String) {
        _newsUiState.update { currentState ->
            currentState.copy(
                searchText = text
            )
        }
    }

    fun updateSearchActiveButton(active: Boolean) {
        _newsUiState.update { currentState ->
            currentState.copy(
                searchBarIsActive = active
            )
        }
    }

    fun insertHistoryItem(historyItem: String) {
        val item = HistoryTable(historyItem)
        viewModelScope.launch {
            repository.insertHistoryItem(item)
        }
    }

    lateinit var item: StateFlow<List<NewsTable>>
    fun getSearchItems(newsList: List<NewsTable>, searchItem: String) {
        _newsUiState.update { currentState ->
            currentState.copy(
                searchItems = newsList.filter {
                    it.title!!.contains(searchItem, ignoreCase = true)
                }
            )
        }
    }

    fun updateTheSearchList() {
        _newsUiState.update { currentState ->
            currentState.copy(
                searchItems = emptyList()
            )
        }
    }

}