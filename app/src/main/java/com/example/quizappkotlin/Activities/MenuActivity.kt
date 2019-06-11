package com.example.quizappkotlin.Activities

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.example.quizappkotlin.*
import com.example.quizappkotlin.Classes.getAccountsDatabaseReference
import com.example.quizappkotlin.Classes.getAuthInstance
import com.example.quizappkotlin.Classes.getCurrentUsername
import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity : AppCompatActivity() {
    var username: String? = ""
    private var auth = getAuthInstance()
    private var fireBaseDatabaseAccounts = getAccountsDatabaseReference()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        overridePendingTransition(R.anim.animation_view_appear,R.anim.animation_view_disappear)


        getCurrentUsername(fireBaseDatabaseAccounts,auth.currentUser?.email,
            object : MyDataCallback {
                override fun onCallback(value: String?) {
                    username = value
                    Toast.makeText(applicationContext, "Username is $username", Toast.LENGTH_SHORT)
                        .show()
                    MenuConstraintLayout.visibility = View.VISIBLE
                    MenuConstraintLayout.startAnimation(getAppearAnimation(applicationContext))
                }
            })
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
