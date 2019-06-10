package com.example.quizappkotlin


import java.util.regex.Pattern

//Patterns
val emailPattern: Pattern = Pattern.compile(
    "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*" +
            "|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])" +
            "*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]" +
            "?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\" +
            "x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])", Pattern.CASE_INSENSITIVE
) // pattern from http://emailregex.com/
val passwordPattern: Pattern = Pattern.compile("^[a-z0-9]{5,12}$", Pattern.CASE_INSENSITIVE)
val userNamePattern: Pattern = Pattern.compile("^[a-z0-9]{5,12}$", Pattern.CASE_INSENSITIVE)

fun checkPattern(pattern: Pattern, userInput: String): Boolean {
    when (pattern) {
        emailPattern -> return pattern.matcher(userInput).find()
        userNamePattern -> return pattern.matcher(userInput).find()
        passwordPattern -> return pattern.matcher(userInput).find()
    }
    return false
}



