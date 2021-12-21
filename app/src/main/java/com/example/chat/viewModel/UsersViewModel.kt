package com.example.chat.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.chat.model.data.Users
import com.example.chat.model.repository.UsersRepository

class UsersViewModel: ViewModel() {
    private val repository = UsersRepository()

    fun insertData(user : Users){
        repository.insertData(user)
    }

    fun getData(): LiveData<MutableList<Users>>{
        return repository.getData()
    }
}