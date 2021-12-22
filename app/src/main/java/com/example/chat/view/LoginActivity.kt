package com.example.chat.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.chat.databinding.ActivityLoginBinding
import com.example.chat.viewModel.LoginViewModel

class LoginActivity : AppCompatActivity() {

    val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        if(PreferenceManager.getString(this,"id")!!.isNotEmpty()){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.login.setOnClickListener {
            login()
        }
    }

    fun login(){
        var id = binding.id.text.toString()
        var pw = binding.password.text.toString()

        when {
            id.isEmpty() -> {
                Toast.makeText(this, "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
            pw.isEmpty() -> {
                Toast.makeText(this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
            else -> {
                viewModel.loginCheck(id,pw).observe(this, Observer {
                    if (it.isNotEmpty()){
                        PreferenceManager.setString(this, "id", id)
                        PreferenceManager.setString(this, "pw", pw)
                        PreferenceManager.setString(this, "name", it[0].name)
//                PreferenceManager.setBoolean(this, "loginCheck", true)

                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else{
                        Toast.makeText(this, "아이디 또는 비밀번호가 맞지 않습니다.", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
    }

}