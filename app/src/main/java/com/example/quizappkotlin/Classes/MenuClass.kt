package com.example.quizappkotlin.Classes

import com.example.quizappkotlin.MyDataCallback
import com.example.quizappkotlin.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

fun getCurrentUsername(myRefAccounts: DatabaseReference, currentUserEmail: String?, myCallback: MyDataCallback) {
    myRefAccounts.addListenerForSingleValueEvent(object : ValueEventListener {

        override fun onDataChange(dataSnapshot: DataSnapshot) {
            for (ds: DataSnapshot in dataSnapshot.children) {
                val userEmail = ds.getValue(User::class.java)?.userEmail
                if (userEmail.equals(currentUserEmail)) {
                    val userNickname = ds.getValue(User::class.java)?.userName
                    myCallback.onCallback(userNickname)
                }
            }
        }

        override fun onCancelled(dataSnapshot: DatabaseError) {

        }

    })
}

