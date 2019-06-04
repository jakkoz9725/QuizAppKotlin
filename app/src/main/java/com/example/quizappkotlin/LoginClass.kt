package com.example.quizappkotlin

import android.widget.ProgressBar
import android.widget.Toast
import java.lang.Exception
import com.google.firebase.auth.*


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
                activity.changeToMenuActivity()
            } else {
                //Check for exception and show a Toast message to user
                try {
                    throw task.exception!!
                } catch (invalidEmail: FirebaseAuthInvalidUserException) {
                    Toast.makeText(activity, "This email is not registered", Toast.LENGTH_SHORT).show()
                } catch (wrongPassword: FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(activity, "Wrong Password", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Toast.makeText(activity,"Error ${e.message}",Toast.LENGTH_SHORT).show()
                }

            }
        }
    progressBar.visibility = ProgressBar.INVISIBLE
}