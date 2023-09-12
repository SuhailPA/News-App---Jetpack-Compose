package com.example.newsapp.ui.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.repository.NewsRepository
import kotlinx.coroutines.launch

class NewsViewModel (val repository : NewsRepository) : ViewModel() {

    init {
        viewModelScope.launch {
            repository.updateNewsContent()
        }
    }
}