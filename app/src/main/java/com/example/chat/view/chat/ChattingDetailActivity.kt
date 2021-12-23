package com.example.chat.view.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chat.R
import com.example.chat.databinding.ActivityChattingDetailBinding
import com.example.chat.model.data.Chat
import com.example.chat.model.data.NotificationBody
import com.example.chat.model.data.Users
import com.example.chat.view.PreferenceManager
import com.example.chat.viewModel.ChatViewModel

class ChattingDetailActivity : AppCompatActivity() {

    val binding by lazy { ActivityChattingDetailBinding.inflate(layoutInflater) }
    lateinit var adapter: ChattingDetailAdapter
    lateinit var viewModel: ChatViewModel
    lateinit var chat_name: String //채팅방 이름

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(ChatViewModel::class.java)

        adapter = ChattingDetailAdapter(this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        chat_name = intent.getStringExtra("chat_name")!!

        supportActionBar?.setTitle(chat_name)

        viewModel.allData(chat_name).observe(this, Observer {
            adapter.dataList = it
            adapter.notifyDataSetChanged()
            binding.recyclerView.scrollToPosition(adapter.dataList.size - 1) //채팅방 안에 들어갔을 때 스크롤이 맨 아래로 가게끔 구현
        })

        binding.submit.setOnClickListener {
            if(binding.content.text.isNotEmpty()) {
                val id = PreferenceManager.getString(this, "id")!!
                val name = PreferenceManager.getString(this, "name")!!

                if (adapter.dataList.isEmpty()) {
                    //채팅방을 사용하는 유저들 리스트와 채팅 메시지 보내기
                    viewModel.initChat(
                        intent.getSerializableExtra("user") as Users,
                        Chat(
                            id,
                            name,
                            binding.content.text.toString(),
                            System.currentTimeMillis()
                        )
                    )
                } else {
                    viewModel.insert(
                        chat_name,
                        Chat(
                            id,
                            name,
                            binding.content.text.toString(),
                            System.currentTimeMillis()
                        )
                    )
                }

                // FCM 전송하기
                val data = NotificationBody.NotificationData(getString(R.string.app_name)
                    ,name,binding.content.text.toString())
                val body = NotificationBody("상대방 토큰",data)

                viewModel.sendNotification(body)

                //응답 여부
                viewModel.myResponse.observe(this, Observer {
                    Log.d("확인", "onViewCreated: $it")
                })

                binding.content.setText("")
            }
        }

    }

}