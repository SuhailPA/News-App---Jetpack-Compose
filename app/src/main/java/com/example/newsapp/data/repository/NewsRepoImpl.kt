package com.example.newsapp.data.repository

import com.example.newsapp.data.model.Article
import com.example.newsapp.data.model.NewsResponseModel
import com.example.newsapp.data.model.NewsTable
import kotlinx.coroutines.flow.Flow

interface NewsRepoImpl {
    fun getAllNews(): Flow<List<NewsTable>>

    suspend fun updateNewsContent()

    suspend fun triggeredFavorite(newsTable: NewsTable)

}