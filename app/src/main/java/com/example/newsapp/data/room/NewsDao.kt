package com.example.newsapp.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.newsapp.data.model.Article
import com.example.newsapp.data.model.NewsResponseModel
import kotlinx.coroutines.flow.Flow


@Dao
interface NewsDao {

    @Insert
    fun updateNews(news : List<Article>)
    @Query("SELECT * FROM newstable")
    fun getAllNews() : Flow<NewsResponseModel>

    @Query("DELETE FROM newstable")
    suspend fun deleteAllNews()

}