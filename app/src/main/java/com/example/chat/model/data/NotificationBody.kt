package com.example.chat.model.data

data class NotificationBody(
    val to: String, // 상대방의 토큰
    val data: NotificationData // 푸시 내용
) {
    data class NotificationData(
        val title: String,
        val userId : String,
        val message: String
    )
}