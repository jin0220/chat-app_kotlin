package com.example.chat.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chat.model.data.NotificationBody
import com.example.chat.model.repository.FCMRepository
import kotlinx.coroutines.launch

class FCMViewModel : ViewModel(){
    private val repository = FCMRepository()
    val myResponse = repository.myResponse

    fun sendNotification(notification: NotificationBody){
        viewModelScope.launch {
            repository.sendNotification(notification)
        }
    }
}