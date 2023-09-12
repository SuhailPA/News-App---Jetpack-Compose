package com.example.newsapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class NewsResponseModel(
    val articles: List<NewsTable>,
    val status: String,
    val totalResults: Int
)