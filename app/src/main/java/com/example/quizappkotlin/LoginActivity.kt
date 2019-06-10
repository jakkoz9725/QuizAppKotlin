package com.example.quizappkotlin

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val database = FirebaseDatabase.getInstance()
        val myRef = database.reference.child("Accounts")
        auth = FirebaseAuth.getInstance()
        //Allow to show&hide password text
        ////LoginClass
        showPasswordOnClickListener(showPasswordToggleBtn, userPassword_ET)
    }
    fun showRegisterLayout(v: View){
        showRegisterLayout(RegisterLayout,LoginLayout)
        onTextChangeListener(newUsernameET,
            usernameRequirement,
            newUserEmailET,
            emailRequirement,
            newUserPasswordET,
            passwordRequirement,
            newUserPasswordRepET,
            passwordRepRequirement)
    }
    // Try to log in to an account with email and password // ON BUTTON CLICK
    fun logIn(v: View) {
        //LoginClass
        logInCheckRequirements(userEmail_ET, userPassword_ET, login_PB, auth, this)
    }
    // log out from currently logged-in account && show login layout // ON BUTTON CLICK
    fun logoutFromCurrentAccount(v: View) {
        //LoginClass
        logoutFromCurrentAccount(auth, AlreadyLoggedLayout, LoginLayout, this)
    }
    //Continue with currently logged account //ON BUTTON CLICK
    fun continueWithCurrentAccount(v: View) {
        //LoginClass
        changeToMenuActivity(this)
    }
    // Check if already logged-in
    @SuppressLint("SetTextI18n")
    public override fun onStart() {
        super.onStart()
        checkCurrentUser(
            auth, LoginLayout,
            AlreadyLoggedLayout,
            whoIsLoggedIn_TextView,
            getString(R.string.already_logged_in_with_email)
        )
    }

}
