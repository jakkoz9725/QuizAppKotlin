package com.example.quizappkotlin.Classes

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import com.example.quizappkotlin.Activities.LoginActivity
import com.example.quizappkotlin.Activities.MenuActivity
import com.example.quizappkotlin.Others.MyDataCallback
import com.example.quizappkotlin.Others.User
import com.example.quizappkotlin.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.dialogue_new_quiz.*
import kotlinx.android.synthetic.main.dialogue_settings.*
import kotlinx.android.synthetic.main.dialogue_settings.loggedUserEmailT

fun getCurrentUsername(myRefAccounts: DatabaseReference, currentUserEmail: String?, myCallback: MyDataCallback) {
    myRefAccounts.addListenerForSingleValueEvent(object : ValueEventListener {

        override fun onDataChange(dataSnapshot: DataSnapshot) {
            for (ds: DataSnapshot in dataSnapshot.children) {
                val userEmail = ds.getValue(User::class.java)?.userEmail
                if (userEmail.equals(currentUserEmail)) {
                    val userNickname = ds.getValue(User::class.java)?.userName
                    myCallback.onCallback(userNickname)
                }
            }
        }
        override fun onCancelled(dataSnapshot: DatabaseError) {

        }
    })
}

fun logOut(auth: FirebaseAuth, applicationContext: Context) {
    auth.signOut()
    applicationContext.startActivity(
        Intent(applicationContext, LoginActivity::class.java).addFlags(
            Intent.FLAG_ACTIVITY_CLEAR_TASK
        ).addFlags(FLAG_ACTIVITY_NEW_TASK)
    )
}

fun showSettingsDialogue(menuActivity: MenuActivity, auth: FirebaseAuth) {
    val dialog = Dialog(menuActivity, R.style.PauseDialog)
    dialog.setContentView(R.layout.dialogue_settings)
    dialog.loggedUserEmailT.text = auth.currentUser?.email.toString()
    dialog.show()
    dialog.logout_Button.setOnClickListener {
        dialog.dismiss()
        menuActivity.logoutAndBackToLoginMenu()
    }
}

fun showQuizCreationDialogue(menuActivity: MenuActivity) {
    val createQuizDialogueClass = CreateQuizDialogueClass(menuActivity)
    //Custom layout
    createQuizDialogueClass.setContentView(R.layout.dialogue_new_quiz)
    //Cut the dialog to two pieces for animation purpose
    createQuizDialogueClass.setLayoutParts(createQuizDialogueClass.firstPiece, createQuizDialogueClass.secondPiece)
    //Transparent background quiz layout
    createQuizDialogueClass.window?.setBackgroundDrawableResource(android.R.color.transparent)

    //Allow to dismiss dialog when click on the top&bottom of the dialog layout
    //Dialog layout covers full screen animation for animation purpose
    createQuizDialogueClass.limitField.setOnClickListener {
        createQuizDialogueClass.dismiss()
    }
    createQuizDialogueClass.limitField2.setOnClickListener {
        createQuizDialogueClass.dismiss()
    }

    createQuizDialogueClass.show()
}

