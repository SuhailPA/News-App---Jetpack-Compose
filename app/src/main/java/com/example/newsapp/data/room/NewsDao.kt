package com.example.newsapp.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.newsapp.data.model.HistoryTable
import com.example.newsapp.data.model.NewsTable
import kotlinx.coroutines.flow.Flow


@Dao
interface NewsDao {

    @Insert
    suspend fun updateNews(news: List<NewsTable>)

    @Query("SELECT * FROM newstable")
    fun getAllNews(): Flow<List<NewsTable>>

    @Query("DELETE FROM newstable")
    suspend fun deleteAllNews()

    @Query("SELECT * FROM newstable LIMIT 1")
    suspend fun getFirstItem(): NewsTable?

    @Update
    suspend fun triggeredFavorite(newsTable: NewsTable)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(historyItem: HistoryTable)

    @Query("SELECT * FROM historytable")
    fun getAllHistoryItems(): Flow<List<HistoryTable>>

    @Query("SELECT * FROM newstable WHERE title LIKE '%'||:history")
    fun getAllItemForSearch(history: String): Flow<List<NewsTable>>
}