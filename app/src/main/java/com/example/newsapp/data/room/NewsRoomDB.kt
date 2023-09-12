package com.example.newsapp.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.newsapp.data.model.NewsTable

@Database(entities = [NewsTable::class], version = 1)
abstract class NewsRoomDB : RoomDatabase() {

    abstract fun newsDao() : NewsDao
}