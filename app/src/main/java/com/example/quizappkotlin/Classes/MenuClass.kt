package com.example.quizappkotlin.Classes

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import com.example.quizappkotlin.Activities.LoginActivity
import com.example.quizappkotlin.MyDataCallback
import com.example.quizappkotlin.User
import com.google.firebase.auth.FirebaseAuth
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
fun logOut(auth: FirebaseAuth, applicationContext: Context) {
    auth.signOut()
    applicationContext.startActivity(
        Intent(applicationContext, LoginActivity::class.java).addFlags(
            Intent.FLAG_ACTIVITY_CLEAR_TASK
        ).addFlags(FLAG_ACTIVITY_NEW_TASK)
    )
}

