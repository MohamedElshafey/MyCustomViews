package com.digitaleg.mycustomviews.customviews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.FrameLayout
import com.digitaleg.mycustomviews.R


open class RoundedCornerLayout : FrameLayout {
    private var CORNER_RADIUS = 10.0f

    private var cornerRadius: Float = 0.toFloat()
    private var topRight: Boolean = false
    private var topLeft: Boolean = false
    private var bottomRight: Boolean = false
    private var bottomLeft: Boolean = false

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {

        val a = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.RoundedCornerLayout,
                0, 0)

        CORNER_RADIUS = a.getInt(R.styleable.RoundedCornerLayout_roundCornerRadius, 0).toFloat()

        topRight = a.getBoolean(R.styleable.RoundedCornerLayout_topRight, false)
        topLeft = a.getBoolean(R.styleable.RoundedCornerLayout_topLeft, false)
        bottomLeft = a.getBoolean(R.styleable.RoundedCornerLayout_bottomLeft, false)
        bottomRight = a.getBoolean(R.styleable.RoundedCornerLayout_bottomRight, false)

        val metrics = context.resources.displayMetrics
        cornerRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, CORNER_RADIUS, metrics)

    }

    private var path = Path()

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        path.reset()

        path = RoundedRect(0f, 0f, width.toFloat(), height.toFloat(), cornerRadius, cornerRadius,
                topLeft, topRight, bottomRight, bottomLeft)
        path.close()
    }

    override fun dispatchDraw(canvas: Canvas) {
        val save = canvas.save()
        canvas.clipPath(path)
        super.dispatchDraw(canvas)
        canvas.restoreToCount(save)
    }

    fun RoundedRect(left: Float, top: Float, right: Float, bottom: Float, rx: Float, ry: Float,
                    tl: Boolean, tr: Boolean, br: Boolean, bl: Boolean): Path {
        var rx = rx
        var ry = ry
        val path = Path()
        if (rx < 0) rx = 0f
        if (ry < 0) ry = 0f
        val width = right - left
        val height = bottom - top
        if (rx > width / 2) rx = width / 2
        if (ry > height / 2) ry = height / 2
        val widthMinusCorners = width - 2 * rx
        val heightMinusCorners = height - 2 * ry

        path.moveTo(right, top + ry)
        if (tr)
            path.rQuadTo(0f, -ry, -rx, -ry)//top-right corner
        else {
            path.rLineTo(0f, -ry)
            path.rLineTo(-rx, 0f)
        }
        path.rLineTo(-widthMinusCorners, 0f)
        if (tl)
            path.rQuadTo(-rx, 0f, -rx, ry) //top-left corner
        else {
            path.rLineTo(-rx, 0f)
            path.rLineTo(0f, ry)
        }
        path.rLineTo(0f, heightMinusCorners)

        if (bl)
            path.rQuadTo(0f, ry, rx, ry)//bottom-left corner
        else {
            path.rLineTo(0f, ry)
            path.rLineTo(rx, 0f)
        }

        path.rLineTo(widthMinusCorners, 0f)
        if (br)
            path.rQuadTo(rx, 0f, rx, -ry) //bottom-right corner
        else {
            path.rLineTo(rx, 0f)
            path.rLineTo(0f, -ry)
        }

        path.rLineTo(0f, -heightMinusCorners)

        path.close()//Given close, last lineto can be removed.

        return path
    }

}