package com.example.newsapp.di

import android.content.Context
import androidx.room.Room
import com.example.newsapp.data.repository.NewsRepository
import com.example.newsapp.data.retrofit.NewsAPI
import com.example.newsapp.data.room.NewsRoomDB
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface NewsContainer {

    val newsRepository: NewsRepository
}

class NewsDataContainer(val context: Context) : NewsContainer {

    val BASE_URL = "https://newsapi.org/v2/"
    val json = Json { ignoreUnknownKeys = true }
    val retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL)
        .build()

    val roomDB: NewsRoomDB = Room.databaseBuilder(
        context = context,
        klass = NewsRoomDB::class.java,
        name = "news_database"
    ).build()

    val newsApi = retrofit.create(NewsAPI::class.java)

    override val newsRepository: NewsRepository by lazy {
        NewsRepository(roomDb = roomDB, newsAPI = newsApi)
    }

}