package com.example.newsapp.data.retrofit

import com.example.newsapp.data.model.NewsResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {

    @GET("top-headlines")
    suspend fun getAllNews(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String
    ): Response<NewsResponseModel>
}