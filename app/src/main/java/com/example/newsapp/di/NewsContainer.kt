package com.example.newsapp.di

import android.content.Context

interface NewsContainer {
}

class NewsDataContainer(val context : Context) : NewsContainer {

}