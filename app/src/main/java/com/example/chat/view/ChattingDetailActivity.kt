package com.example.chat.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chat.databinding.ActivityChattingDetailBinding
import com.example.chat.model.data.Chat
import com.example.chat.viewModel.ChatViewModel

class ChattingDetailActivity : AppCompatActivity() {

    val binding by lazy { ActivityChattingDetailBinding.inflate(layoutInflater) }
    lateinit var adapter: ChattingDetailAdapter
    lateinit var viewModel: ChatViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(ChatViewModel::class.java)

        adapter = ChattingDetailAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.allData.observe(this, Observer {
            adapter.dataList = it
            adapter.notifyDataSetChanged()
            binding.recyclerView.scrollToPosition(adapter.dataList.size - 1) //채팅방 안에 들어갔을 때 스크롤이 맨 아래로 가게끔 구현
        })

        binding.submit.setOnClickListener {
            if(binding.content.text.isNotEmpty()) {
                viewModel.insert(
                    Chat(
                        "나",
                        binding.content.text.toString(),
                        System.currentTimeMillis(),
                        1
                    )
                )
                binding.content.setText("")
            }
        }

    }

}