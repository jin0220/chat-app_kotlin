package com.example.chat.model.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chat.model.data.Chat
import com.example.chat.model.data.NotificationBody
import com.example.chat.model.data.Users
import com.example.chat.view.PreferenceManager
import com.example.chat.view.RetrofitClient
import com.google.firebase.database.*
import okhttp3.ResponseBody
import retrofit2.Response


class ChatRepository {
    // Write a message to the database
    private val database = FirebaseDatabase.getInstance()
    private val databaseReference = database.getReference("chat")

    fun initChat(users : MutableList<Users>, chat: Chat){
        val key = System.currentTimeMillis().toString()
        databaseReference.child(key).child("users").setValue(users)
        databaseReference.child(key).child("message").push().setValue(chat)
    }

    // 대화방 리스트
    fun chatList(userName:String) : LiveData<MutableList<MutableMap<String,String>>>{
        var mutableList = MutableLiveData<MutableList<MutableMap<String,String>>>()
        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var list : MutableList<MutableMap<String,String>> = mutableListOf()
                var name = ""
                var token = ""
                if(snapshot.exists()){
                    for(chat in snapshot.children){
                        for(user in chat.child("users").children) {
                            if (userName != user.getValue(Users::class.java)!!.name) {
                                name += user.getValue(Users::class.java)!!.name
                                token = user.getValue(Users::class.java)!!.token
                            }
                        }
//                        list.add(Pair(chat.key!!, name))
                        list.add(mutableMapOf("key" to chat.key!!, "name" to name, "token" to token))
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
            override fun onDataChange(snapshot: DataSnapshot) {
                val listData: MutableList<Chat> = mutableListOf()
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