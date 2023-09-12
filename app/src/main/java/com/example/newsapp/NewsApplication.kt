package com.example.newsapp

import android.app.Application
import com.example.newsapp.di.NewsContainer
import com.example.newsapp.di.NewsDataContainer

class NewsApplication : Application() {

    lateinit var container: NewsContainer

    override fun onCreate() {
        super.onCreate()
        container = NewsDataContainer(this)
    }
}