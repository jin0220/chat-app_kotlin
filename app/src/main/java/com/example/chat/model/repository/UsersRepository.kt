package com.example.chat.model.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chat.model.data.Chat
import com.example.chat.model.data.Users
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class UsersRepository {
    private val database = FirebaseDatabase.getInstance()
    private val databaseReference = database.getReference("users")

    fun AllData() : LiveData<MutableList<Users>>{
        val mutableList = MutableLiveData<MutableList<Users>>()
        databaseReference.addValueEventListener(object : ValueEventListener {
            val listData: MutableList<Users> = mutableListOf()
            override fun onDataChange(snapshot: DataSnapshot) {
                listData.clear()
                if(snapshot.exists()){
                    for(user in snapshot.children){
                        listData.add(user.getValue(Users::class.java)!!)
                    }
                    mutableList.value = listData
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        return mutableList
    }

    fun insertData(user : Users){
        databaseReference.child(user.phone).setValue(user)
    }

    fun getData(phone: String): LiveData<MutableList<Users>>{
        val mutableList = MutableLiveData<MutableList<Users>>()
        databaseReference.child(phone).addValueEventListener(object : ValueEventListener{
            val listData: MutableList<Users> = mutableListOf()
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    listData.add(snapshot.getValue(Users::class.java)!!)
                    mutableList.value = listData
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        return mutableList
    }
}