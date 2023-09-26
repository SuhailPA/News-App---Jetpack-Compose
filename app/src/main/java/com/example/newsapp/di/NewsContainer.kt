package com.example.newsapp.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.example.newsapp.data.model.NewsTable
import com.example.newsapp.data.repository.NewsRemoteMediator
import com.example.newsapp.data.repository.NewsRepository
import com.example.newsapp.data.retrofit.NewsAPI
import com.example.newsapp.data.retrofit.NotificationAPI
import com.example.newsapp.data.room.NewsRoomDB
import com.example.newsapp.utils.OAuthInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface NewsContainer {

    val newsRepository: NewsRepository
    val pager: Pager<Int, NewsTable>
}

class NewsDataContainer(context: Context) : NewsContainer {

    val NOTIFI_BASE_URL = "https://fcm.googleapis.com/v1/projects/newsapp-13024/"

    val NOTIFI_ACCESSTOKEN = "115199700977251930876"

    val CONTENT_TYPE = "application/json"

    private val BASE_URL = "https://newsapi.org/v2/"

    private val json = Json { ignoreUnknownKeys = true }

    private fun okHttpInterceptor(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder().addInterceptor(
            OAuthInterceptor(accessToken = NOTIFI_ACCESSTOKEN)
        ).addInterceptor(interceptor)
            .build()
    }

    private val retrofitWithToken: Retrofit = Retrofit.Builder()
        .baseUrl(NOTIFI_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpInterceptor())
        .build()


    private val retrofit: Retrofit.Builder = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL)


    private val roomDB: NewsRoomDB = Room.databaseBuilder(
        context = context,
        klass = NewsRoomDB::class.java,
        name = "news_database"
    ).build()

    private val newsApi: NewsAPI = retrofit.build().create(NewsAPI::class.java)

    private val notificationAPI: NotificationAPI =
        retrofitWithToken.create(NotificationAPI::class.java)

    override val newsRepository: NewsRepository by lazy {
        NewsRepository(roomDb = roomDB, newsAPI = newsApi, notificationAPI = notificationAPI)
    }



    @OptIn(ExperimentalPagingApi::class)
    override val pager: Pager<Int, NewsTable> by lazy {
        Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = NewsRemoteMediator(
                roomDB = roomDB,
                newsApi = newsApi
            ),
            pagingSourceFactory = {
                roomDB.newsDao().pagingSource()
            }
        )
    }

}