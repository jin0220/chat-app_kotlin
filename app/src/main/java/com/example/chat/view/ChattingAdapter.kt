package com.example.chat.view

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.chat.databinding.RecyclerChattingBinding
import com.example.chat.model.data.Users

class ChattingAdapter : RecyclerView.Adapter<ChattingAdapter.Holder>(){

    var dataList:List<Users> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = RecyclerChattingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        return holder.bind(dataList[position])
    }

    override fun getItemCount(): Int = dataList.size

    inner class Holder(val binding: RecyclerChattingBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user:Users){
            with(binding) {
                contentBox.setOnClickListener {
                    val intent = Intent(contentBox.context, ChattingDetailActivity::class.java)
                    ContextCompat.startActivity(contentBox.context, intent, null)
                }
            }
        }
    }

    fun addItem(list: List<Users>){
        this.dataList = list
        notifyDataSetChanged()
    }
}