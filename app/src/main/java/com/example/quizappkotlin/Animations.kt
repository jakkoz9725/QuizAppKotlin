package com.example.quizappkotlin

import android.content.Context
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils


fun animationViewDisappear(viewToDisappear: View, viewToAppear: View, applicationContext: Context) {
    val animationDisappear: Animation = AnimationUtils.loadAnimation(applicationContext, R.anim.animation_view_disappear)
    animationDisappear.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationRepeat(animation: Animation?) {

        }

        override fun onAnimationEnd(animation: Animation?) {
        animationViewAppear(viewToAppear,applicationContext)

        }

        override fun onAnimationStart(animation: Animation?) {
            //block screen
            viewToDisappear.visibility = View.GONE
        }

    })
    viewToDisappear.startAnimation(animationDisappear)
}
private fun animationViewAppear(viewToAppear: View, applicationContext: Context){
    val animationAppear: Animation = AnimationUtils.loadAnimation(applicationContext, R.anim.animation_view_appear)
    animationAppear.setAnimationListener(object: Animation.AnimationListener{
        override fun onAnimationRepeat(animation: Animation?) {

        }

        override fun onAnimationEnd(animation: Animation?) {
            //unlock screen

        }

        override fun onAnimationStart(animation: Animation?) {
            viewToAppear.visibility = View.VISIBLE
        }

    })
    viewToAppear.startAnimation(animationAppear)
}