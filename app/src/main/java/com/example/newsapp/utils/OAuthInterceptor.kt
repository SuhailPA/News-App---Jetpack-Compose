package com.example.newsapp.utils

import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Response

class OAuthInterceptor(val accessToken: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = accessToken
        val request = chain.request()
        val newRequest = request.newBuilder().headers(
            Headers.headersOf(
                "Authorization", "Bearer $accessToken",
                "Content-Type", "application/json"
            )
        ).build()
        return chain.proceed(newRequest)
    }

}