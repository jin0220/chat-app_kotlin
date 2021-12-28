package com.example.chat.view.friends

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import com.example.chat.databinding.ActivityProfileBinding
import com.example.chat.model.data.Users
import com.example.chat.view.chat.ChattingDetailActivity

class ProfileActivity : AppCompatActivity() {

    val binding by lazy { ActivityProfileBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE) //타이틀 바 없애기
        setContentView(binding.root)

        val dm = applicationContext.resources.displayMetrics
        var width = (dm.widthPixels * 0.8).toInt()
        var height = (dm.heightPixels * 0.7).toInt()

        window.attributes.width = width
        window.attributes.height = height

        val name = intent.getStringExtra("name")
        binding.name.text = name

        binding.startChat.setOnClickListener {
            val intent = Intent(this, ChattingDetailActivity::class.java)
            intent.putExtra("chat_name", name)
//            intent.putExtra("key", )
            intent.putExtra("user", intent.getSerializableExtra("user"))
            startActivity(intent)
        }
    }
}