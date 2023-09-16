package com.example.newsapp.data.repository

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.newsapp.data.model.NewsTable
import com.example.newsapp.data.retrofit.NewsAPI
import com.example.newsapp.data.room.NewsRoomDB
import kotlinx.coroutines.delay
import okio.IOException
import retrofit2.HttpException

@OptIn(ExperimentalPagingApi::class)
class NewsRemoteMediator(
    val roomDB: NewsRoomDB,
    val newsApi: NewsAPI
) : RemoteMediator<Int, NewsTable>() {

    var currentPageNo = 1
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, NewsTable>
    ): MediatorResult {
        return try {

            val loadKey = when (loadType) {
                LoadType.REFRESH -> {
                    1
                }

                LoadType.PREPEND -> {
                    return MediatorResult.Success(
                        endOfPaginationReached = true
                    )
                }

                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        1
                    } else {
                      ++currentPageNo
                    }
                }

            }


            val newsList = newsApi.getPaginatedNews(
                country = "in",
                apiKey = "857417c014944bc7b92fcbd3cf22c8a1",
                pageSize = state.config.pageSize,
                page = loadKey
            )

            roomDB.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    roomDB.newsDao().deleteAllNews()
                }
                val newsEntries = newsList.body()?.articles ?: listOf()
                roomDB.newsDao().updateNews(newsEntries)
            }

            MediatorResult.Success(
                endOfPaginationReached = newsList.body()?.articles?.isEmpty() ?: true
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }

    }

}