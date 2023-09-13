package com.example.newsapp.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.newsapp.data.model.Article
import com.example.newsapp.data.model.NewsResponseModel
import com.example.newsapp.data.model.NewsTable
import kotlinx.coroutines.flow.Flow


@Dao
interface NewsDao {

    @Insert
    suspend fun updateNews(news : List<NewsTable>)
    @Query("SELECT * FROM newstable")
    fun getAllNews() : Flow<List<NewsTable>>

    @Query("DELETE FROM newstable")
    suspend fun deleteAllNews()

    @Query("SELECT * FROM newstable LIMIT 1")
    suspend fun getFirstItem() : NewsTable

}