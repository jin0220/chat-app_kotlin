package com.example.chat.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.chat.model.data.Users
import com.example.chat.model.repository.LoginRepository

class LoginViewModel: ViewModel() {
    private val repository = LoginRepository()

    fun loginCheck(id: String, pw: String) : LiveData<MutableList<Users>>{
        return repository.loginCheck(id, pw)
    }
}