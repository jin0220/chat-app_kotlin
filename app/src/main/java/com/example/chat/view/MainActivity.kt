package com.example.chat.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.chat.R
import com.example.chat.databinding.ActivityMainBinding
import com.example.chat.model.data.Users
import com.example.chat.view.chat.ChattingFragment
import com.example.chat.view.friends.FriendsListFragment
import com.example.chat.view.setting.SettingFragment
import com.example.chat.viewModel.UsersViewModel
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    lateinit var viewModel:UsersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[UsersViewModel::class.java]

        val list = listOf(FriendsListFragment(), ChattingFragment(), SettingFragment())

        supportFragmentManager.beginTransaction().add(R.id.frameLayout, list[0]).commit()

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.first -> {
                    supportFragmentManager.beginTransaction().replace(R.id.frameLayout, list[0])
                        .commit()
                    true
                }
                R.id.second -> {
                    supportFragmentManager.beginTransaction().replace(R.id.frameLayout, list[1])
                        .commit()
                    true
                }
                R.id.third -> {
                    supportFragmentManager.beginTransaction().replace(R.id.frameLayout, list[2])
                        .commit()
                    true
                }
                else -> {
                    supportFragmentManager.beginTransaction().replace(R.id.frameLayout, list[0])
                        .commit()
                    true
                }
            }
        }
        getToken()
    }

    // 토큰 가져오기
    fun getToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            if (!it.isSuccessful)
                Log.d("FCM token failed", "${it.exception}")
            else{
//                viewModel.insertData(Users("01012341235","123","김철수", "", it.result.toString()))
                PreferenceManager.setString(this, "token", it.result.toString())
                Log.d("FCM token", "${it.result}")
            }

        }
    }
}