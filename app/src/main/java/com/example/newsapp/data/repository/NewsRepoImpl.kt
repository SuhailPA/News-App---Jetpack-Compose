package com.example.newsapp.data.repository

import com.example.newsapp.data.model.NewsResponseModel
import kotlinx.coroutines.flow.Flow

interface NewsRepoImpl {
    fun getAllNews() : Flow<NewsResponseModel>

    suspend fun updateNewsContent()
}