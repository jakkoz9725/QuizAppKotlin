package com.example.quizappkotlin.Activities

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.quizappkotlin.*
import com.example.quizappkotlin.Classes.*
import com.example.quizappkotlin.Others.animationViewDisappear
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {
    private lateinit var currentlyLoggedInUserEmail: String
    private lateinit var auth: FirebaseAuth
    private val database = FirebaseDatabase.getInstance()
    private val accountDatabaseReference = database.reference.child("Accounts")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        overridePendingTransition(R.anim.animation_view_appear, R.anim.animation_view_disappear)
        auth = FirebaseAuth.getInstance()
        //      Allow to show&hide password text field
        //      LoginClass
        showPasswordOnClickListener(showPasswordToggleBtn, userPassword_ET)
    }

    //Show LoginLayout and hide Register layout && set onTextChangeListeners
    fun showRegisterLayout(@Suppress("UNUSED_PARAMETER")v: View) {
        // Animation Class
        animationViewDisappear(LoginLayout, RegisterLayout, this)
        clearLoginLayout()

        onTextChangeListener(
            newUsernameET,
            usernameRequirement,
            newUserEmailET,
            emailRequirement,
            newUserPasswordET,
            passwordRequirement,
            newUserPasswordRepET,
            passwordRepRequirement
        )
    }

    //Show LoginLayout and hide Register layout
    fun showLoginLayout(@Suppress("UNUSED_PARAMETER")v: View) {
//        RegisterLayout.visibility = View.GONE
//        LoginLayout.visibility = View.VISIBLE
        animationViewDisappear(RegisterLayout, LoginLayout, this)
        clearRegisterLayout()
    }

    private fun clearLoginLayout() {
        userEmail_ET.setText("")
        userPassword_ET.setText("")
    }

    private fun clearRegisterLayout() {
        newUsernameET.setText("")
        newUserEmailET.setText("")
        newUserPasswordET.setText("")
        newUserPasswordRepET.setText("")
    }

    // Try to log in to an account with email and password // ON BUTTON CLICK
    fun logIn(@Suppress("UNUSED_PARAMETER")v: View) {
        //LoginClass
        logInCheckRequirements(userEmail_ET, userPassword_ET, login_PB, auth, this)
    }

    // log out from currently logged-in account && show login layout // ON BUTTON CLICK
    fun logoutFromCurrentAccount(@Suppress("UNUSED_PARAMETER")v: View) {
        //LoginClass
        logoutFromCurrentAccount(auth, AlreadyLoggedLayout, LoginLayout, this)
    }

    //Continue with currently logged account //ON BUTTON CLICK
    fun continueWithCurrentAccount(@Suppress("UNUSED_PARAMETER")v: View) {
        //LoginClass
        changeToMenuActivity(this, currentlyLoggedInUserEmail)
        finish()
    }

    // Create account
    fun createAccount(@Suppress("UNUSED_PARAMETER")v: View) {
        val username = newUsernameET.text.toString()
        val userPassword = newUserPasswordET.text.toString()
        val userPasswordRep = newUserPasswordRepET.text.toString()
        val userEmail = newUserEmailET.text.toString()

        checkUserInput(
            username,
            userEmail,
            userPassword,
            userPasswordRep,
            this,
            registerPB,
            accountDatabaseReference
        )
    }

    // Check if already logged-in
    @SuppressLint("SetTextI18n")
    public override fun onStart() {
        super.onStart()
        currentlyLoggedInUserEmail = checkCurrentUser(
            auth, LoginLayout,
            AlreadyLoggedLayout,
            whoIsLoggedIn_TextView,
            getString(R.string.already_logged_in_with_email)
        )
    }
}
