package com.example.newsapp.data.model

data class NewsResponseModel(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)