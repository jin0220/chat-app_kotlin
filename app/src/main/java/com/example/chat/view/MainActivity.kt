package com.example.chat.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.chat.R
import com.example.chat.databinding.ActivityMainBinding
import com.example.chat.view.chat.ChattingFragment
import com.example.chat.view.friends.FriendsListFragment
import com.example.chat.view.setting.SettingFragment

class MainActivity : AppCompatActivity() {

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val list = listOf(FriendsListFragment(), ChattingFragment(), SettingFragment())

        supportFragmentManager.beginTransaction().add(R.id.frameLayout, list[0]) .commit()

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.first -> {
                    supportFragmentManager.beginTransaction().replace(R.id.frameLayout, list[0]) .commit()
                    true
                }
                R.id.second -> {
                    supportFragmentManager.beginTransaction().replace(R.id.frameLayout, list[1]) .commit()
                    true
                }
                R.id.third -> {
                    supportFragmentManager.beginTransaction().replace(R.id.frameLayout, list[2]) .commit()
                    true
                }
                else -> {
                    supportFragmentManager.beginTransaction().replace(R.id.frameLayout, list[0]) .commit()
                    true
                }
            }
        }

    }
}