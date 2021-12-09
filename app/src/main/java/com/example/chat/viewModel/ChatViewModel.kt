package com.example.chat.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chat.model.data.Chat
import com.example.chat.model.repository.ChatRepository
import kotlinx.coroutines.launch

class ChatViewModel: ViewModel() {
    private val repository = ChatRepository()

    //채팅방 리스트
    val chatList = repository.chatList()

    // 대화 내용
    fun insert(chat: Chat){
        repository.setData(chat)
    }

    val allData : LiveData<MutableList<Chat>> = repository.getData()
}