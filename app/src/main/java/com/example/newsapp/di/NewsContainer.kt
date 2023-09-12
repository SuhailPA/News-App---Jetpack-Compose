package com.example.newsapp.di

import android.content.Context
import androidx.room.Room
import com.example.newsapp.data.room.NewsRoomDB

interface NewsContainer {
}

class NewsDataContainer(val context: Context) : NewsContainer {


    val roomDB: NewsRoomDB = Room.databaseBuilder(
        context = context,
        NewsRoomDB::class.java,
        "news_db"
    )
        .build()
}