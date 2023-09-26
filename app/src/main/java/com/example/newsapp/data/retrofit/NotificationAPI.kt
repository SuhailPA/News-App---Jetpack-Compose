package com.example.newsapp.data.retrofit

import com.example.newsapp.data.model.MainPushModel
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface NotificationAPI {

    @POST("messages:send")
    suspend fun postNotification(
        @Body notification: MainPushModel
    ) : Response<ResponseBody>
}