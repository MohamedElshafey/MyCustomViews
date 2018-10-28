package com.digitaleg.mycustomviews.customviews

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.digitaleg.mycustomviews.R


class ImageSwitcherCard : RoundedCornerLayout {

    var animatedImageView: AnimatedImageView

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs!!, 0)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        val inflater = LayoutInflater.from(context)

        val view = inflater.inflate(R.layout.image_switcher_card, this)
        animatedImageView = view.findViewById(R.id.animatedImageView);
        val gradient = view.findViewById<View>(R.id.gradient)
        val textView = view.findViewById<TextView>(R.id.textView);

        val imgSwitcher = context.theme.obtainStyledAttributes(
                attrs, R.styleable.ImageSwitcherCard, 0, 0)

        val imageResource1 = imgSwitcher.getDrawable(R.styleable.ImageSwitcherCard_switchImage1)
        val imageResource2 = imgSwitcher.getDrawable(R.styleable.ImageSwitcherCard_switchImage2)
        val imageResource3 = imgSwitcher.getDrawable(R.styleable.ImageSwitcherCard_switchImage3)
        val gradientResource = imgSwitcher.getDrawable(R.styleable.ImageSwitcherCard_gradient);
        val name = imgSwitcher.getString(R.styleable.ImageSwitcherCard_name);

        textView.text = name
        gradient.background = gradientResource

        val resources = ArrayList<Drawable>()

        if (imageResource1 != null)
            resources.add(imageResource1)

        if (imageResource2 != null)
            resources.add(imageResource2)

        if (imageResource3 != null)
            resources.add(imageResource3)

        animatedImageView.setDrawables(resources)
    }

    fun startAnimate() {
        animatedImageView.startAnimate()
    }

}