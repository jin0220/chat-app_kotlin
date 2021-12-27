package com.example.chat.view.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.view.WindowManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chat.R
import com.example.chat.databinding.ActivityChattingDetailBinding
import com.example.chat.model.data.Chat
import com.example.chat.model.data.NotificationBody
import com.example.chat.model.data.Users
import com.example.chat.view.KeyboardVisibilityUtils
import com.example.chat.view.PreferenceManager
import com.example.chat.viewModel.ChatViewModel
import com.example.chat.viewModel.FCMViewModel
import com.example.chat.viewModel.UsersViewModel

class ChattingDetailActivity : AppCompatActivity() {

    val binding by lazy { ActivityChattingDetailBinding.inflate(layoutInflater) }
    lateinit var adapter: ChattingDetailAdapter
    lateinit var chatViewModel: ChatViewModel
    lateinit var userViewModel: UsersViewModel
    lateinit var fcmViewModel: FCMViewModel
    lateinit var chat_name: String //채팅방 이름
    lateinit var key: String //채팅방 키값
    lateinit var users: MutableList<Users>
    var lastHeight : Int = 0
    private lateinit var keyboardVisibilityUtils: KeyboardVisibilityUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        chatViewModel = ViewModelProvider(this).get(ChatViewModel::class.java)
        userViewModel = ViewModelProvider(this).get(UsersViewModel::class.java)
        fcmViewModel = ViewModelProvider(this).get(FCMViewModel::class.java)

        // 현재 사용자의 정보 가져오기
        val phone = PreferenceManager.getString(this, "id")!!
        userViewModel.getData(phone).observe(this, {
            users = it
        })

        init()

        keyboardVisibilityUtils = KeyboardVisibilityUtils(window,
            onShowKeyboard = {
                binding.recyclerView.scrollToPosition(adapter.dataList.size - 1)
            },
            onHideKeyboard = {
                binding.recyclerView.scrollToPosition(adapter.dataList.size - 1)
            }
        )

    }

    fun init() {
        // 어댑터 연결
        adapter = ChattingDetailAdapter(this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        chat_name = intent.getStringExtra("chat_name")!!
        key = intent.getStringExtra("key")!!
        supportActionBar?.title = chat_name

        chatViewModel.allData(key).observe(this, Observer {
            adapter.dataList = it
            adapter.notifyDataSetChanged()
            binding.recyclerView.scrollToPosition(adapter.dataList.size - 1) //채팅방 안에 들어갔을 때 스크롤이 맨 아래로 가게끔 구현
        })
    }


    fun sendMessage(view: View) {
        if (binding.content.text.isNotEmpty()) {
            val id = PreferenceManager.getString(this, "id")!!
            val name = PreferenceManager.getString(this, "name")!!

            if (adapter.dataList.isEmpty()) {
                users.add(intent.getSerializableExtra("user") as Users)
                //채팅방을 사용하는 유저들 리스트와 채팅 메시지 보내기
                chatViewModel.initChat(
                    users,
                    Chat(
                        id,
                        name,
                        binding.content.text.toString(),
                        System.currentTimeMillis()
                    )
                )
            } else {
                chatViewModel.insert(
                    key,
                    Chat(
                        id,
                        name,
                        binding.content.text.toString(),
                        System.currentTimeMillis()
                    )
                )
            }

            // FCM 전송하기
            val data = NotificationBody.NotificationData(
                getString(R.string.app_name), name, binding.content.text.toString()
            )
            val body = NotificationBody(intent.getStringExtra("token").toString(), data)

            fcmViewModel.sendNotification(body)

            //응답 여부
            fcmViewModel.myResponse.observe(this, Observer {
                Log.d("확인", "onViewCreated: $it")
            })

            binding.content.setText("")
        }

    }
}