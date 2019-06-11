package com.example.quizappkotlin.Classes

import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.example.quizappkotlin.*
import com.example.quizappkotlin.Activities.LoginActivity
import com.example.quizappkotlin.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_login.*
import java.lang.Exception
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
    setTextWatcher(
        newUsernameET,
        usernameRequirement,
        userNamePattern
    )
    setTextWatcher(
        newUserEmailET,
        emailRequirement,
        emailPattern
    )
    setTextWatcher(
        newUserPasswordET,
        passwordRequirement,
        passwordPattern
    )
    setTextWatcherForPassword(
        newUserPasswordET,
        newUserPasswordRepET,
        passwordRepRequirement
    )
    setTextWatcherForPassword(
        newUserPasswordRepET,
        newUserPasswordET,
        passwordRepRequirement
    )

}

// Check if user input is correct and change UI
fun setTextWatcher(editText: EditText, textView: TextView, pattern: Pattern) {
    editText.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            if (checkPattern(pattern, editText.text.toString())) {
                textView.setTextColor(Color.GREEN)
                textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_correct_green_mark, 0, 0, 0)
            } else {
                textView.setTextColor(Color.RED)
                textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_incorrect_red_mark, 0, 0, 0)
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
            if (password1.text.toString().equals(password2.text.toString()) && password1.text.toString() != "") {
                textView.setTextColor(Color.GREEN)
                textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_correct_green_mark, 0, 0, 0)
            } else {
                textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_incorrect_red_mark, 0, 0, 0)
                textView.setTextColor(Color.RED)
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }
    })
}

//If user data is correct try to create account with email and password
fun checkUserInput(
    userName: String,
    userEmail: String,
    userPassword: String,
    userRepPassword: String,
    loginActivity: LoginActivity,
    registerProgressBar: ProgressBar,
    databaseReference: DatabaseReference

) {
    if (checkPattern(userNamePattern, userName)
        && checkPattern(emailPattern, userEmail)
        && checkPattern(passwordPattern, userPassword)
        && userPassword.equals(userRepPassword)
    ) {
        val newUser = User(userName, userEmail, userPassword)
        createNewAccount(
            newUser,
            registerProgressBar,
            databaseReference,
            loginActivity
        )
    } else {
        Toast.makeText(loginActivity, "Check the requirements", Toast.LENGTH_SHORT).show()
    }
}


fun createNewAccount(
    userInput: User,
    registerProgressBar: ProgressBar,
    databaseReference: DatabaseReference,
    loginActivity: LoginActivity
) {
    val usernameCorrect =
        checkPattern(userNamePattern, userInput.userName)
    val emailCorrect =
        checkPattern(emailPattern, userInput.userEmail)
    val passwordCorrect =
        checkPattern(passwordPattern, userInput.userPassword)


    if (usernameCorrect && passwordCorrect && emailCorrect) {
        val listOfUsers = arrayListOf<String>()
        registerProgressBar.visibility = ProgressBar.VISIBLE
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (ds: DataSnapshot in dataSnapshot.children) {
                    listOfUsers.add(ds.value.toString().toLowerCase())
                }
                if (listOfUsers.contains(userInput.userName.toLowerCase())) {
                    Toast.makeText(loginActivity, "Username already exists", Toast.LENGTH_SHORT).show()
                    registerProgressBar.visibility = ProgressBar.GONE
                } else {
                    val accountCreation = FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                        userInput.userEmail,
                        userInput.userPassword
                    )
                    accountCreation.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            databaseReference.child(userInput.userName).setValue(userInput)
                            registerProgressBar.visibility = ProgressBar.GONE
                            Toast.makeText(
                                loginActivity,
                                "${userInput.userName} account created!",
                                Toast.LENGTH_SHORT
                            ).show()
                            loginActivity.RegisterLayout.visibility = View.GONE
                            loginActivity.LoginLayout.visibility = View.VISIBLE

                        } else try {
                            task.exception
                        } catch (emailExists: FirebaseAuthUserCollisionException) {
                            Toast.makeText(loginActivity, "User already exists", Toast.LENGTH_SHORT).show()
                        } catch (otherExceptions: Exception) {
                            Toast.makeText(loginActivity, "Exception :$otherExceptions", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(loginActivity, "Error ${p0.code}", Toast.LENGTH_SHORT).show()
                registerProgressBar.visibility = ProgressBar.GONE

            }
        })
    }
}
