package com.example.quizappkotlin

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth

class MenuActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        auth = FirebaseAuth.getInstance()
    }

    //Logout and clear task, prevent back-press button to comeback to this activity w/o logged user
    fun logoutAndBackToLoginMenu(v: View) {
        auth.signOut()
        startActivity(
            Intent(this, LoginActivity::class.java).addFlags(
                Intent.FLAG_ACTIVITY_CLEAR_TASK
            ).addFlags(FLAG_ACTIVITY_NEW_TASK)
        )
    }
}
