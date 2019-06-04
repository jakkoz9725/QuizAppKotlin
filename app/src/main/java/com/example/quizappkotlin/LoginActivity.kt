package com.example.quizappkotlin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val database = FirebaseDatabase.getInstance()
        val myRef = database.reference.child("Accounts")
        auth = FirebaseAuth.getInstance()


        //Allow to show&hide password text
        showPasswordToggleBtn.setOnClickListener{
            if (userPassword_ET.transformationMethod == HideReturnsTransformationMethod.getInstance()) {
                userPassword_ET.transformationMethod = PasswordTransformationMethod.getInstance()
            } else {
                userPassword_ET.transformationMethod = HideReturnsTransformationMethod.getInstance()
            }
        }
    }

    // Method called from LoginClass after successfully login
    fun changeToMenuActivity() {
        startActivity(Intent(this, MenuActivity::class.java))
    }

    // Login button onClick method
    fun logIn(v: View) {
        val userEmail = userEmail_ET.text.toString()
        val userPassword = userPassword_ET.text.toString()
        //If pattern is correct, try to log-in
        if(loginMenuCheckUserInput(emailPattern,userEmail,this)){
            login_PB.visibility = ProgressBar.VISIBLE
            logInWithEmailAndPassword(auth, this, userEmail, userPassword, login_PB)
        }
    }

    // Check if already logged-in
    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            Toast.makeText(applicationContext, "User ${currentUser.email}", Toast.LENGTH_SHORT)
                .show()
        } else {
            Toast.makeText(applicationContext, "There is noone logged in", Toast.LENGTH_SHORT)
                .show()
        }
    }
}
