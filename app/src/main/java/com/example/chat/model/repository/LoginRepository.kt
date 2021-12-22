package com.example.chat.model.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chat.model.data.Users
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LoginRepository {
    private val database = FirebaseDatabase.getInstance()
    private val databaseReference = database.getReference("users")

    fun loginCheck(id:String, pw:String) : LiveData<MutableList<Users>> {
        val mutableList = MutableLiveData<MutableList<Users>>()

        databaseReference.addValueEventListener(object : ValueEventListener {
            val listData: MutableList<Users> = mutableListOf()
            override fun onDataChange(snapshot: DataSnapshot) {
                listData.clear()
                if(snapshot.exists()){
                    for(user in snapshot.children){
                        if (id == user.getValue(Users::class.java)!!.phone
                            && pw == user.getValue(Users::class.java)!!.password){
                            listData.add(user.getValue(Users::class.java)!!)
                        }
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
}