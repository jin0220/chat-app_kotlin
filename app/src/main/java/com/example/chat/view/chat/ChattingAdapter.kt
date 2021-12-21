package com.example.chat.view.chat

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.chat.databinding.RecyclerChattingBinding
import com.example.chat.viewModel.ChatViewModel

class ChattingAdapter(var viewModel: ChatViewModel) : RecyclerView.Adapter<ChattingAdapter.Holder>(){

    var dataList:MutableList<String> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = RecyclerChattingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        return holder.bind(dataList[position])
    }

    override fun getItemCount(): Int = dataList.size

    inner class Holder(val binding: RecyclerChattingBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user:String){
            with(binding) {
                roomName.text = user
                contentBox.setOnClickListener {
                    val intent = Intent(contentBox.context, ChattingDetailActivity::class.java)
                    intent.putExtra("chat_name", user)
                    ContextCompat.startActivity(contentBox.context, intent, null)
                }

                contentBox.setOnLongClickListener {
                    viewModel.chatDelete(user)
                    dataList.clear() // 배열이 중복으로 쌓이는 것을 해결하기 위해 사용
                    return@setOnLongClickListener true
                }
            }
        }
    }

    fun addItem(list: MutableList<String>){
        this.dataList = list
        notifyDataSetChanged()
    }
}