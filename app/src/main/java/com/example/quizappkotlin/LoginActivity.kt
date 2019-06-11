package com.example.quizappkotlin

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val database = FirebaseDatabase.getInstance()
    private val accountDatabaseReference = database.reference.child("Accounts")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()
        //Allow to show&hide password text
        ////LoginClass
        showPasswordOnClickListener(showPasswordToggleBtn, userPassword_ET)
    }

    //Show LoginLayout and hide Register layout && set onTextChangeListeners
    fun showRegisterLayout(v: View) {
        //        RegisterLayout.visibility = View.VISIBLE
        //        LoginLayout.visibility = View.GONE
        // Animation Classw
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
    fun showLoginLayout(v: View) {
//        RegisterLayout.visibility = View.GONE
//        LoginLayout.visibility = View.VISIBLE
        animationViewDisappear(RegisterLayout, LoginLayout, this)
        clearRegisterLayout()
    }
    fun clearLoginLayout(){
        userEmail_ET.setText("")
        userPassword_ET.setText("")
    }
    fun clearRegisterLayout(){
        newUsernameET.setText("")
        newUserEmailET.setText("")
        newUserPasswordET.setText("")
        newUserPasswordRepET.setText("")
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

    // Create account
    fun createAccount(v: View) {
        val username = newUsernameET.text.toString()
        val userpassword = newUserPasswordET.text.toString()
        val userpasswordRep = newUserPasswordRepET.text.toString()
        val userEmail = newUserEmailET.text.toString()

        checkUserInput(
            username,
            userEmail,
            userpassword,
            userpasswordRep,
            this,
            registerPB,
            accountDatabaseReference
        )
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
