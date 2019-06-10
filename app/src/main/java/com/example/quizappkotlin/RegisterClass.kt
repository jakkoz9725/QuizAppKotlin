package com.example.quizappkotlin

import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import java.util.regex.Pattern

fun onTextChangeListener(
    newUsernameET: EditText,
    usernameRequirement: TextView,
    newUserEmailET: EditText,
    emailRequirement: TextView,
    newUserPasswordET: EditText,
    passwordRequirement: TextView,
    newUserPasswordRepET: EditText,
    passwordRepRequirement: TextView
) {
    setTextWatcher(newUsernameET, usernameRequirement, userNamePattern)
    setTextWatcher(newUserEmailET, emailRequirement, emailPattern)
    setTextWatcher(newUserPasswordET, passwordRequirement, passwordPattern)
    setTextWatcherForPassword(newUserPasswordET, newUserPasswordRepET, passwordRepRequirement)
    setTextWatcherForPassword(newUserPasswordRepET, newUserPasswordET, passwordRepRequirement)

}

fun setTextWatcher(editText: EditText, textView: TextView, pattern: Pattern) {
    editText.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            if (checkPattern(pattern, editText.text.toString())) {
                textView.setTextColor(Color.GREEN)
            } else {
                textView.setTextColor(Color.RED)
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }
    })
}

fun setTextWatcherForPassword(password1: EditText, password2: EditText, textView: TextView) {
    password1.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            if (password1.text.toString().equals(password2.text.toString())) {
                textView.setTextColor(Color.GREEN)
            } else {
                textView.setTextColor(Color.RED)
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }
    })
}

