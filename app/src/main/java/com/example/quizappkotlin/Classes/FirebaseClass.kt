package com.example.quizappkotlin.Classes

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


fun getAccountsDatabaseReference(): DatabaseReference {
    return FirebaseDatabase.getInstance().getReference("Accounts")
}

fun getQuizzesDatabaseReference(): DatabaseReference {
    return FirebaseDatabase.getInstance().getReference("Quizzes")
}

fun getAuthInstance() : FirebaseAuth{
    return FirebaseAuth.getInstance()
}