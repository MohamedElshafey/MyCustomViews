package com.digitaleg.mycustomviews.customviews

import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.animation.LinearInterpolator
import com.digitaleg.mycustomviews.R

class AnimationDiagonalBackground : ConstraintLayout {
    private val duration = 100000L
    private var drawableResource = R.drawable.background_repeat
    private lateinit var view1: View
    private lateinit var view2: View

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.animation_diagonal_background, this)

        val displayMetrics = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels
        val width = displayMetrics.widthPixels

        val backgroundOne = view.findViewById<View>(R.id.background_one)
        backgroundOne.layoutParams = ConstraintLayout.LayoutParams(width, height * 2)
        animateOne(backgroundOne)

        val backgroundTwo = view.findViewById<View>(R.id.background_two)
        backgroundTwo.layoutParams = ConstraintLayout.LayoutParams(width, height * 3)
        animateTwo(backgroundTwo)
    }

    private fun animateOne(backgroundOne: View) {

        val animator = ValueAnimator.ofFloat(0f, 1f)
        animator.repeatCount = ValueAnimator.INFINITE
        animator.interpolator = LinearInterpolator()
        animator.duration = duration

        animator.addUpdateListener { animation ->
            val progress = animation.animatedValue as Float
            val width = backgroundOne.width.toFloat()
            val height = backgroundOne.height.toFloat()
            val translationX = width * progress
            val translationY = height / 2 * progress
            backgroundOne.translationX = translationX
            backgroundOne.translationY = -translationY
        }
        animator.start()
    }


    private fun animateTwo(backgroundTwo: View) {
        val animator = ValueAnimator.ofFloat(0f, 1f)
        animator.repeatCount = ValueAnimator.INFINITE
        animator.interpolator = LinearInterpolator()
        animator.duration = duration

        animator.addUpdateListener { animation ->
            val progress = animation.animatedValue as Float
            val width = backgroundTwo.width.toFloat()
            val height = backgroundTwo.height.toFloat()
            val translationX = width * progress
            val translationY = height / 3 * progress

            backgroundTwo.translationX = translationX - width
            backgroundTwo.translationY = -translationY
        }
        animator.start()
    }

}