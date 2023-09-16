package com.example.newsapp.data.repository

import com.example.newsapp.data.model.HistoryTable
import com.example.newsapp.data.model.NewsTable
import kotlinx.coroutines.flow.Flow

interface NewsRepoImpl {
    fun getAllNews(): Flow<List<NewsTable>>

    suspend fun updateNewsContent()

    suspend fun triggeredFavorite(newsTable: NewsTable)

    suspend fun insertHistoryItem(historyItem: HistoryTable)

    fun getAllHistoryItems(): Flow<List<HistoryTable>>

}