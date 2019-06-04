package com.example.quizappkotlin

import android.content.Context
import android.content.Intent
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

fun logInWithEmailAndPassword(auth: FirebaseAuth,activity: LoginActivity, userEmail: String, userPassword: String,progressBar: ProgressBar){
    auth.signInWithEmailAndPassword(userEmail, userPassword)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(activity, "Authentication successfully.",
                    Toast.LENGTH_SHORT).show()
                activity.changeToMenuActivity()
            } else {
                Toast.makeText(activity, "Authentication failed.",
                    Toast.LENGTH_SHORT).show()
            }
            progressBar.visibility = ProgressBar.INVISIBLE
        }
}