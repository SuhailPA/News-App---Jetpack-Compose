package com.example.newsapp.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import com.example.newsapp.data.model.HistoryTable
import com.example.newsapp.data.model.MainPushModel
import com.example.newsapp.data.model.NewsTable
import com.example.newsapp.data.model.NewsUiState
import com.example.newsapp.data.model.NotificationData
import com.example.newsapp.data.model.PushNotification
import com.example.newsapp.data.repository.NewsRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

const val TAG = "FCM Notification"

class NewsViewModel(
    private val repository: NewsRepository,
    pager: Pager<Int, NewsTable>
) : ViewModel() {

    val pagerFlow = pager.flow.cachedIn(viewModelScope)

    private val _newsUiState = MutableStateFlow(NewsUiState())
    val newsUiState: StateFlow<NewsUiState> = _newsUiState

    private val _notificationState = MutableStateFlow(NotificationData())
    val notificationState: StateFlow<NotificationData> = _notificationState


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

    fun notificationScreenTitleUpdate(text: String) {
        _notificationState.update { currentState ->
            currentState.copy(
                title = text
            )
        }
    }

    fun notificationScreenDescriptionUpdate(text: String) {
        _notificationState.update { currentState ->
            currentState.copy(
                body = text
            )
        }
    }

    fun insertHistoryItem(historyItem: String) {
        val item = HistoryTable(historyItem)
        viewModelScope.launch {
            repository.insertHistoryItem(item)
        }
    }

    fun getSearchItems(newsList: List<NewsTable>, searchItem: String) {
        _newsUiState.update { currentState ->
            currentState.copy(
                searchItems = newsList.filter {
                    it.title.contains(searchItem, ignoreCase = true)
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

    fun sendPushNotification() {
        val notificationData = MainPushModel(
            PushNotification(
                NotificationData(
                    _notificationState.value.title,
                    _notificationState.value.body
                ), "news"
            )
        )
        viewModelScope.launch {
            try {
                val response = repository.sendNotification(notificationData)
                if (response.isSuccessful) {
                    Log.i(TAG, "Response : ${Gson().toJson(response)}")
                } else {
                    Log.i(TAG, response.errorBody().toString())
                }
            } catch (e: Exception) {
                Log.i(TAG, e.toString())
            }
        }
    }

}