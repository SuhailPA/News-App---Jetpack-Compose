package com.example.newsapp.data.repository

import com.example.newsapp.data.model.Article
import com.example.newsapp.data.model.NewsResponseModel
import com.example.newsapp.data.model.NewsTable
import com.example.newsapp.data.retrofit.NewsAPI
import com.example.newsapp.data.room.NewsDao
import kotlinx.coroutines.flow.Flow

class NewsRepository(val newsDao: NewsDao, val newsAPI: NewsAPI) : NewsRepoImpl {
    override fun getAllNews(): Flow<List<NewsTable>> = newsDao.getAllNews()

    override suspend fun updateNewsContent() {
        val news = newsAPI.getAllNews(country = "in", apiKey = "857417c014944bc7b92fcbd3cf22c8a1")
        if (news.isSuccessful && news.body()?.status == "ok") {
            news.body()?.articles?.let {
                newsDao.deleteAllNews()
                newsDao.updateNews(it)
            }
        }
    }
}