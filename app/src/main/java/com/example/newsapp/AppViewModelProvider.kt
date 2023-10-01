package com.example.newsapp

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.newsapp.ui.NewsViewModel

object AppViewModelProvider {

    val Factory = viewModelFactory {
        initializer {
            val application =
                this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as NewsApplication
            NewsViewModel(application.container.newsRepository, application.container.pager)
        }
    }
}