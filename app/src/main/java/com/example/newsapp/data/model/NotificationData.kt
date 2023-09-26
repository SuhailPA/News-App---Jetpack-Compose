package com.example.newsapp.data.model

data class NotificationData(
    val title : String = "",
    val body : String = ""
)

data class PushNotification(
    val data: NotificationData,
    val topic : String
)
data class MainPushModel(
    val message : PushNotification
)