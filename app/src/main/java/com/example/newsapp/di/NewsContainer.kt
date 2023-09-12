package com.example.newsapp.di

import android.content.Context
import androidx.room.Room
import com.example.newsapp.data.room.NewsRoomDB
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface NewsContainer {
}

class NewsDataContainer(val context: Context) : NewsContainer {

    val BASE_URL = "https://newsapi.org/v2/"

    val roomDB: NewsRoomDB = Room.databaseBuilder(
        context = context,
        NewsRoomDB::class.java,
        "news_db")
        .build()

    val retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL)
        .build()
}