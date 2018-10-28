package com.digitaleg.mycustomviews.customviews

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.ImageView
import com.digitaleg.mycustomviews.R


class AnimatedImageView : FrameLayout {

    private var resources = ArrayList<Drawable>()

    lateinit var imageBack: ImageView
    lateinit var imageFront: ImageView
    var oldIndex: Int = 0
    var newIndex: Int = 0

    fun setDrawables(value: ArrayList<Drawable>) {
        resources = value;
        init(value)
    }

    constructor(context: Context) : this(context, null)

    constructor(context: Context, att: AttributeSet?) : this(context, att, 0)

    constructor(context: Context, att: AttributeSet?, defStyleAttr: Int) : super(context, att, defStyleAttr) {
        initializeImages(context)
    }

    private fun initializeImages(context: Context) {
        imageBack = ImageView(context)
        imageBack.id = generateViewId()
        imageBack.layoutParams = LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        imageBack.scaleType = ImageView.ScaleType.CENTER_CROP

        imageFront = ImageView(context)
        imageFront.id = generateViewId()
        imageFront.layoutParams = LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        imageFront.scaleType = ImageView.ScaleType.CENTER_CROP

        addView(imageBack)
        addView(imageFront)
    }

    private fun init(value: ArrayList<Drawable>) {
        if (value.size > 1) {
            newIndex = 0;
            oldIndex = 0;

            imageBack.setImageDrawable(value[newIndex])
            imageFront.setImageDrawable(value[newIndex])
        } else {
            if (value.size > 0) {
                oldIndex = 0;
                newIndex = 0;
                imageBack.setImageDrawable(value[newIndex])
                imageFront.setImageDrawable(value[newIndex])
            }
        }

    }

    fun startAnimate() {
        newIndex++;
        newIndex = {
            if (newIndex == resources.size)
                0
            else
                newIndex
        }.invoke()
        val oldIndex = {
            if (newIndex == 0)
                resources.size - 1
            else
                (newIndex - 1)
        }
        imageViewAnimatedChange(context, resources[newIndex], resources[oldIndex.invoke()])
    }

    private fun imageViewAnimatedChange(c: Context, newImage: Drawable, oldImage: Drawable) {
        val animIn = AnimationUtils.loadAnimation(c, getRandomAnimation())
        animIn.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {
                imageBack.setImageDrawable(newImage)
            }

            override fun onAnimationStart(p0: Animation?) {
                imageBack.setImageDrawable(oldImage)
                imageFront.setImageDrawable(newImage)
            }
        })

        imageFront.startAnimation(animIn)
    }

    private fun getRandomAnimation(): Int {
        val animResources = listOf(R.anim.bottom_to_original, R.anim.left_to_original,
                R.anim.right_to_original, R.anim.top_to_original);
        return animResources.shuffled().take(1)[0]
    }
}