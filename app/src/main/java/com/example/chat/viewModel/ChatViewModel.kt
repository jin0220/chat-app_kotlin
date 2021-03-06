package com.example.chat.viewModel

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chat.model.data.Chat
import com.example.chat.model.data.NotificationBody
import com.example.chat.model.data.Users
import com.example.chat.model.repository.ChatRepository
import kotlinx.coroutines.launch

class ChatViewModel: ViewModel() {
    private val repository = ChatRepository()

    fun initChat(users: MutableList<Users>, chat: Chat) {
        repository.initChat(users, chat)
    }

    //채팅방 리스트
    fun chatList(userName: String) = repository.chatList(userName)

    fun chatDelete(chat_name:String){
        repository.chatDelete(chat_name)
    }

    // 대화 내용
    fun insert(chat_name: String, chat: Chat){
        repository.setData(chat_name, chat)
    }

    fun allData(chat_name:String) : LiveData<MutableList<Chat>> = repository.getData(chat_name)
}