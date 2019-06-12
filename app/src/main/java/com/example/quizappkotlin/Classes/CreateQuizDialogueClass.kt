package com.example.quizappkotlin.Classes

import android.app.Dialog
import android.content.Context
import android.support.constraint.ConstraintLayout
import android.view.animation.Animation
import com.example.quizappkotlin.Others.*


class CreateQuizDialogueClass(context: Context) : Dialog(context) {

    private lateinit var upperPart: ConstraintLayout
    private lateinit var bottomPart: ConstraintLayout


    fun setLayoutParts(upperPart: ConstraintLayout, bottomPart: ConstraintLayout) {
        this.upperPart = upperPart
        this.bottomPart = bottomPart
    }

    override fun show() {
        super.show()
        upperPart.startAnimation(getAppearFromTopAnimation(context))
        bottomPart.startAnimation(getAppearFromBottom(context))
    }

    override fun cancel() {
        val animation = getDisappearFromTopAnimation(context)
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                bottomPart.startAnimation(getDisappearFromBottom(context))

            }

            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                dismiss()
            }

        })
        upperPart.startAnimation(animation)
    }
}