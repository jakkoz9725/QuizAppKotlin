package com.example.quizappkotlin

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.support.constraint.ConstraintLayout
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.*
import java.lang.Exception
import com.google.firebase.auth.*
import kotlinx.android.synthetic.main.activity_login.*

fun logInCheckRequirements(
    userEmail_ET: EditText,
    userPassword_ET: EditText,
    login_PB: ProgressBar,
    auth: FirebaseAuth,
    loginActivity: LoginActivity
) {
    val userEmail = userEmail_ET.text.toString()
    val userPassword = userPassword_ET.text.toString()
    //If pattern is correct, try to log-in
    if (checkPattern(emailPattern, userEmail)) {
        if (checkPattern(passwordPattern, userPassword)) {
            login_PB.visibility = ProgressBar.VISIBLE
            logInWithEmailAndPassword(auth, loginActivity, userEmail, userPassword, login_PB)
        } else {
            Toast.makeText(loginActivity, "Password format is incorrect", Toast.LENGTH_SHORT).show()
        }
    } else {
        Toast.makeText(loginActivity, "Email format incorrect", Toast.LENGTH_SHORT).show()
    }
}

fun logInWithEmailAndPassword(
    auth: FirebaseAuth,
    activity: LoginActivity,
    userEmail: String,
    userPassword: String,
    progressBar: ProgressBar
) {
    auth.signInWithEmailAndPassword(userEmail, userPassword)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(
                    activity, "Authentication successfully.",
                    Toast.LENGTH_SHORT
                ).show()
                changeToMenuActivity(activity)
            } else {
                //Check for exception and show a Toast message to user
                try {
                    throw task.exception!!
                } catch (invalidEmail: FirebaseAuthInvalidUserException) {
                    Toast.makeText(activity, "This email is not registered", Toast.LENGTH_SHORT).show()
                } catch (wrongPassword: FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(activity, "Wrong Password", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Toast.makeText(activity, "Error ${e.message}", Toast.LENGTH_SHORT).show()
                }

            }
        }
    progressBar.visibility = ProgressBar.INVISIBLE
}

fun logoutFromCurrentAccount(
    auth: FirebaseAuth,
    AlreadyLoggedLayout: ConstraintLayout,
    LoginLayout: ConstraintLayout,
    context: Context
) {
    if (auth.currentUser != null) {
        auth.signOut()
        AlreadyLoggedLayout.visibility = ConstraintLayout.GONE
        LoginLayout.visibility = ConstraintLayout.VISIBLE
    } else {
        Toast.makeText(context, "Smth is wrong", Toast.LENGTH_SHORT).show()
    }
}

@SuppressLint("SetTextI18n")
fun checkCurrentUser(
    auth: FirebaseAuth,
    LoginLayout: ConstraintLayout,
    AlreadyLoggedLayout: ConstraintLayout,
    whoIsLoggedIn_TextView: TextView,
    already_logged_in_with_email: String
) {
    val currentUser = auth.currentUser
    if (currentUser != null) {
        LoginLayout.visibility = ConstraintLayout.GONE
        AlreadyLoggedLayout.visibility = ConstraintLayout.VISIBLE
        whoIsLoggedIn_TextView.text = already_logged_in_with_email + "\n ${currentUser.email}"
    } else {
        LoginLayout.visibility = ConstraintLayout.VISIBLE
        AlreadyLoggedLayout.visibility = ConstraintLayout.GONE
    }
}

fun changeToMenuActivity(loginActivity: LoginActivity): Intent {
    return Intent(loginActivity, MenuActivity::class.java).addFlags(
        Intent.FLAG_ACTIVITY_CLEAR_TASK
    ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
}

fun showPasswordOnClickListener(showPasswordToggleBtn: ToggleButton, userPassword_ET: EditText) {
    showPasswordToggleBtn.setOnClickListener {
        if (userPassword_ET.transformationMethod == HideReturnsTransformationMethod.getInstance()) {
            userPassword_ET.transformationMethod = PasswordTransformationMethod.getInstance()
        } else {
            userPassword_ET.transformationMethod = HideReturnsTransformationMethod.getInstance()
        }
    }
}
fun showRegisterLayout(RegisterLayout: ConstraintLayout, LoginLayout: ConstraintLayout){
    LoginLayout.visibility = ConstraintLayout.GONE
    RegisterLayout.visibility = ConstraintLayout.VISIBLE
}