package com.example.chat.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chat.R
import com.example.chat.databinding.RecyclerChatLeftBinding
import com.example.chat.databinding.RecyclerChatRightBinding
import com.example.chat.model.data.Chat

class ChattingDetailAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    var dataList:List<Chat> = listOf()

    override fun getItemViewType(position: Int): Int {
        return dataList[position].type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View?
        return when(viewType){
            0 -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_chat_left, parent,false)
                LeftHolder(view)
            }
            else -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_chat_right, parent,false)
                RightHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(dataList[position].type){
            0 -> {
                (holder as LeftHolder).bind(dataList[position])
                holder.setIsRecyclable(false)
            }
            else -> {
                (holder as RightHolder).bind(dataList[position])
                        holder.setIsRecyclable(false)
            }
        }
    }

    override fun getItemCount(): Int = dataList.size

    inner class LeftHolder(view: View): RecyclerView.ViewHolder(view){
        fun bind(chat: Chat){

        }
    }

    inner class RightHolder(view: View): RecyclerView.ViewHolder(view){
        fun bind(chat: Chat){

        }
    }


}