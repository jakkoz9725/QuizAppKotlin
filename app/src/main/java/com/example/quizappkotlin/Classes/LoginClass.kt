package com.example.quizappkotlin.Classes

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.support.constraint.ConstraintLayout
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.*
import com.example.quizappkotlin.Activities.LoginActivity
import com.example.quizappkotlin.Activities.MenuActivity
import com.example.quizappkotlin.Others.checkPattern
import com.example.quizappkotlin.Others.emailPattern
import com.example.quizappkotlin.Others.passwordPattern
import java.lang.Exception
import com.google.firebase.auth.*

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
        if (checkPattern(
                passwordPattern,
                userPassword
            )
        ) {
            login_PB.visibility = ProgressBar.VISIBLE
            logInWithEmailAndPassword(
                auth,
                loginActivity,
                userEmail,
                userPassword,
                login_PB
            )
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
    progressBar.visibility = ProgressBar.VISIBLE
    auth.signInWithEmailAndPassword(userEmail, userPassword)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                progressBar.visibility = ProgressBar.GONE
                changeToMenuActivity(activity, userEmail)
            } else {
                try {
                    throw task.exception!!
                } catch (invalidEmail: FirebaseAuthInvalidUserException) {
                    Toast.makeText(activity, "This email is not registered", Toast.LENGTH_SHORT).show()
                } catch (wrongPassword: FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(activity, "Wrong Password", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Toast.makeText(activity, "Error ${e.message}", Toast.LENGTH_SHORT).show()
                }
                progressBar.visibility = ProgressBar.GONE
            }
        }
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
): String {
    val currentUser = auth.currentUser
    val currentUserEmail: String
    if (currentUser != null) {
        currentUserEmail = currentUser.email.toString()
        LoginLayout.visibility = ConstraintLayout.GONE
        AlreadyLoggedLayout.visibility = ConstraintLayout.VISIBLE
        whoIsLoggedIn_TextView.text = "$already_logged_in_with_email\n $currentUserEmail"
    } else {
        LoginLayout.visibility = ConstraintLayout.VISIBLE
        AlreadyLoggedLayout.visibility = ConstraintLayout.GONE
        currentUserEmail = ""
    }
    return currentUserEmail
}

fun changeToMenuActivity(loginActivity: LoginActivity, userEmail: String) {
    loginActivity.startActivity(
        Intent(loginActivity, MenuActivity::class.java).putExtra("currentlyLoggedInUserEmail", userEmail).addFlags(
            Intent.FLAG_ACTIVITY_CLEAR_TASK
        ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    )
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

