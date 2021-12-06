package com.example.chat.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chat.databinding.RecyclerFriendsListBinding
import com.example.chat.model.data.Users

class FriendsAdapter: RecyclerView.Adapter<FriendsAdapter.Holder>() {

    var dataList:List<Users> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = RecyclerFriendsListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        return holder.bind(dataList[position])
    }

    override fun getItemCount(): Int = dataList.size

    inner class Holder(val binding: RecyclerFriendsListBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(user:Users){
            binding.name.text = user.name
        }
    }

    fun addItem(list: List<Users>){
        this.dataList = list
        notifyDataSetChanged()
    }

}