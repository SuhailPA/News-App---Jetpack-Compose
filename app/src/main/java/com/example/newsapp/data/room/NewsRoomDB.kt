package com.example.newsapp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [NewsRoomDB::class], version = 0)
abstract class NewsRoomDB : RoomDatabase() {

    abstract fun newsDao() : NewsDao
}