package com.example.chat.model.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chat.model.data.Chat
import com.example.chat.view.ChattingAdapter
import com.google.firebase.database.*


class ChatRepository {
    // Write a message to the database
    private val database = FirebaseDatabase.getInstance()
    private val databaseReference = database.getReference("chat")

    // 대화방 리스트
    fun chatList() : LiveData<List<String>>{
        var mutableList = MutableLiveData<List<String>>()
        databaseReference.addValueEventListener(object : ValueEventListener{
            var list : MutableList<String> = mutableListOf()
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(chat in snapshot.children){
                        list.add(chat.key!!)
                    }
                    mutableList.value = list
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        return mutableList
    }

    // 대화 내용
    fun setData(chat: Chat) {
        databaseReference.child("chat_name").push().setValue(chat)
    }

    fun getData() : LiveData<MutableList<Chat>>{
        val mutableList = MutableLiveData<MutableList<Chat>>()
        databaseReference.child("chat_name").addValueEventListener(object : ValueEventListener{
            val listData: MutableList<Chat> = mutableListOf()
            override fun onDataChange(snapshot: DataSnapshot) {
                listData.clear()
                if(snapshot.exists()){
                    for(chat in snapshot.children){
                        listData.add(chat.getValue(Chat::class.java)!!)
                    }
                    mutableList.value = listData
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
//        databaseReference.addChildEventListener(object :ChildEventListener{
//            val listData: MutableList<Chat> = mutableListOf()
//            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
//                if(snapshot.exists()){
//                    for(chat in snapshot.children){
//                        listData.add(chat.getValue(Chat::class.java)!!)
//                    }
//                    mutableList.value = listData
//                }
//            }
//
//            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
//
//            }
//
//            override fun onChildRemoved(snapshot: DataSnapshot) {
//
//            }
//
//            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
//
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//
//            }
//
//        })
        return  mutableList
    }
}