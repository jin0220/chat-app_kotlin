package com.example.chat.model.repository

import androidx.lifecycle.MutableLiveData
import com.example.chat.model.data.NotificationBody
import com.example.chat.view.RetrofitClient
import okhttp3.ResponseBody
import retrofit2.Response

class FCMRepository {
    val myResponse : MutableLiveData<Response<ResponseBody>> = MutableLiveData() // 메세지 수신 정보

    // 푸시 메세지 전송
    suspend fun sendNotification(notification: NotificationBody) {
        myResponse.value = RetrofitClient.api.sendNotification(notification)
    }
}