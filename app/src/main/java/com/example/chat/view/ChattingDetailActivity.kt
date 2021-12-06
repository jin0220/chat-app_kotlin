package com.example.chat.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chat.databinding.ActivityChattingDetailBinding
import com.example.chat.model.data.Chat

class ChattingDetailActivity : AppCompatActivity() {

    val binding by lazy { ActivityChattingDetailBinding.inflate(layoutInflater) }
    lateinit var adapter: ChattingDetailAdapter

    val datas = mutableListOf<Chat>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        adapter = ChattingDetailAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        datas.apply {
            add(Chat("상대방", "안녕하세요", "11:34 PM", 0))
            add(Chat("나", "네 안녕하세요", "11:34 PM", 1))

            adapter.dataList = datas
            adapter.notifyDataSetChanged()
        }
    }
}