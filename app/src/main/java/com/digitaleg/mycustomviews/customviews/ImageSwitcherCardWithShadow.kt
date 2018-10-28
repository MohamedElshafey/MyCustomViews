package com.digitaleg.mycustomviews.customviews

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import com.digitaleg.mycustomviews.customviews.ImageSwitcherCard
import com.gigamole.library.ShadowLayout

class ImageSwitcherCardWithShadow : ShadowLayout {

    var imageSwitcherCard: ImageSwitcherCard

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs!!, 0)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {

        imageSwitcherCard = ImageSwitcherCard(context, attrs)

        addView(imageSwitcherCard)

        val dps = 4f
        val pxs = dps * resources.displayMetrics.density

        super.setShadowDistance(pxs)
        super.setShadowRadius(pxs * 3)
        super.setShadowColor(Color.parseColor("#000000"))
        super.setIsShadowed(true)
        super.setShadowAngle(45f)
    }

    fun startAnimate() {
        imageSwitcherCard.startAnimate()
    }
}