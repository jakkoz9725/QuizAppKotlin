package com.example.quizappkotlin


import android.app.Activity
import android.util.Log
import android.widget.Toast
import java.util.regex.Pattern

const val TAG: String = "Patterns"
val emailPattern: Pattern = Pattern.compile(
    "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*" +
    "|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])" +
    "*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]" +
    "?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\" +
    "x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])", Pattern.CASE_INSENSITIVE) // pattern from http://emailregex.com/
val passwordPattern: Pattern = Pattern.compile("[^a-z0-9]", Pattern.CASE_INSENSITIVE)
val userNamePattern: Pattern = Pattern.compile("[^a-z0-9]", Pattern.CASE_INSENSITIVE)

fun loginMenuCheckUserInput(pattern: Pattern, emailAddress: String, activity: Activity): Boolean{
    when (pattern) {
        emailPattern -> return if(pattern.matcher(emailAddress).find()){
            Log.d(TAG,"email is correctly formatted")
            true
        }else{
            Log.d(TAG,"email is badly formatted $emailAddress")
            Toast.makeText(activity,"Wrong email format",Toast.LENGTH_SHORT).show()
            false
        }
        passwordPattern -> pattern.matcher(emailAddress).find()
        userNamePattern -> pattern.matcher(emailAddress).find()
    }
return false
}