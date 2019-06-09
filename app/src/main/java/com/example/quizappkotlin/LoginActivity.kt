package com.example.quizappkotlin

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
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
        startActivity(Intent(this, MenuActivity::class.java).addFlags(
            Intent.FLAG_ACTIVITY_CLEAR_TASK
        ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
    }

    // Try to log in to an account with email and password // ON BUTTON CLICK
    fun logIn(v: View) {
        val userEmail = userEmail_ET.text.toString()
        val userPassword = userPassword_ET.text.toString()
        //If pattern is correct, try to log-in
        if(loginMenuCheckUserInput(emailPattern,userEmail,this)){
            login_PB.visibility = ProgressBar.VISIBLE
            logInWithEmailAndPassword(auth, this, userEmail, userPassword, login_PB)
        }
    }
    // log out from currently logged-in account && show login layout // ON BUTTON CLICK
    fun logoutFromCurrentAccount(v: View){
        if(auth.currentUser != null){
            auth.signOut()
            AlreadyLoggedLayout.visibility = ConstraintLayout.GONE
            LoginLayout.visibility = ConstraintLayout.VISIBLE
        }else{
            Toast.makeText(this,"Smth is wrong",Toast.LENGTH_SHORT).show()
        }
    }
    //Continue with currently logged account //ON BUTTON CLICK
    fun continueWithCurrentAccount(v: View){
        changeToMenuActivity()
    }
    // Check if already logged-in
    @SuppressLint("SetTextI18n")
    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            LoginLayout.visibility = ConstraintLayout.GONE
            AlreadyLoggedLayout.visibility = ConstraintLayout.VISIBLE
            whoIsLoggedIn_TextView.text = getString(R.string.already_logged_in_with_email) + "\n ${currentUser.email}"
        } else {
            LoginLayout.visibility = ConstraintLayout.VISIBLE
            AlreadyLoggedLayout.visibility = ConstraintLayout.GONE
        }
    }
}
