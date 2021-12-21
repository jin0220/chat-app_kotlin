package com.example.chat.model.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chat.model.data.Chat
import com.example.chat.model.data.Users
import com.google.firebase.database.*


class ChatRepository {
    // Write a message to the database
    private val database = FirebaseDatabase.getInstance()
    private val databaseReference = database.getReference("chat")

    fun initChat(users : Users, chat: Chat){
        databaseReference.child(users.name).child("users").push().setValue(users)
        databaseReference.child(users.name).child("message").push().setValue(chat)
    }

    // 대화방 리스트
    fun chatList() : LiveData<MutableList<String>>{
        var mutableList = MutableLiveData<MutableList<String>>()
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

    fun chatDelete(chat_name:String) {
        databaseReference.child(chat_name).removeValue()
    }

    // 대화 내용
    fun setData(chat_name: String, chat: Chat) {
        databaseReference.child(chat_name).child("message").push().setValue(chat)
    }

    fun getData(chat_name: String) : LiveData<MutableList<Chat>>{
        val mutableList = MutableLiveData<MutableList<Chat>>()
        databaseReference.child(chat_name).child("message").addValueEventListener(object : ValueEventListener{
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