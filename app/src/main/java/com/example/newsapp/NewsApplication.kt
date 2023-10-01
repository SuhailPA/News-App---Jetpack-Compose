package com.example.newsapp

import android.app.Application
import com.example.newsapp.di.NewsContainer
import com.example.newsapp.di.NewsDataContainer
import com.google.firebase.messaging.FirebaseMessaging

class NewsApplication : Application() {

    lateinit var container: NewsContainer

    override fun onCreate() {
        super.onCreate()
        FirebaseMessaging.getInstance().subscribeToTopic("News")
        container = NewsDataContainer(this)
    }
}