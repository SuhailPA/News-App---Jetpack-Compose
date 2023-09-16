package com.example.newsapp.data.repository

import com.example.newsapp.data.model.HistoryTable
import com.example.newsapp.data.model.NewsTable
import com.example.newsapp.data.retrofit.NewsAPI
import com.example.newsapp.data.room.NewsDao
import com.example.newsapp.data.room.NewsRoomDB
import kotlinx.coroutines.flow.Flow

class NewsRepository(private val roomDb: NewsRoomDB, val newsAPI: NewsAPI) : NewsRepoImpl {
    override fun getAllNews(): Flow<List<NewsTable>> = roomDb.newsDao().getAllNews()

    override suspend fun updateNewsContent() {
        try {
            val news =
                newsAPI.getAllNews(country = "in", apiKey = "857417c014944bc7b92fcbd3cf22c8a1")
            if (news.isSuccessful && news.body()?.status == "ok") {
                news.body()?.articles?.let {
                    if (it[0].title?.equals(
                            roomDb.newsDao().getFirstItem()?.title
                        ) == false || roomDb.newsDao()
                            .getFirstItem() == null
                    ) {
                        with(roomDb) {
                            newsDao().deleteAllNews()
                            newsDao().updateNews(it)
                        }
                    }
                }
            }
        } catch (e: Exception) {

        }

    }

    override suspend fun triggeredFavorite(newsTable: NewsTable) {
        roomDb.newsDao().triggeredFavorite(newsTable)
    }

    override suspend fun insertHistoryItem(historyItem: HistoryTable) {
        roomDb.newsDao().insertHistory(historyItem)
    }

    override fun getAllHistoryItems(): Flow<List<HistoryTable>> {
        return roomDb.newsDao().getAllHistoryItems()
    }

    override fun getItemForSearch(item: String): Flow<List<NewsTable>> {
        return roomDb.newsDao().getAllItemForSearch(item)
    }
}