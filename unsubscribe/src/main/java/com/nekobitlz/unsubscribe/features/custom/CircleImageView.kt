package com.nekobitlz.unsubscribe.features.custom

import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import androidx.annotation.ColorRes
import androidx.annotation.Dimension
import com.nekobitlz.unsubscribe.R
import com.nekobitlz.unsubscribe.utils.convertDpToPx

open class CircleImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): androidx.appcompat.widget.AppCompatImageView(context, attrs, defStyleAttr) {

    companion object {
        private const val DEFAULT_BORDER_COLOR = Color.TRANSPARENT
        private const val DEFAULT_BORDER_WIDTH_DP = 2f
    }

    private val defaultBorderWidth = context.resources.getDimension(R.dimen.circle_border_size_2)
    private var borderColor = DEFAULT_BORDER_COLOR
    private var borderWidth = (context as Activity).convertDpToPx(defaultBorderWidth)

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val clipPath = Path()

    init {
        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView)

            borderColor = a.getColor(
                R.styleable.CircleImageView_cv_borderColor,
                DEFAULT_BORDER_COLOR
            )

            borderWidth = a.getDimension(
                R.styleable.CircleImageView_cv_borderWidth,
                borderWidth
            )

            a.recycle()
        }
    }

    fun setBorderColor(@ColorRes colorId: Int) {
        borderColor = resources.getColor(colorId)
        invalidate()
    }

    fun setBorderWidth(@Dimension dp: Float) {
        borderWidth = (context as Activity).convertDpToPx(dp)
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        val x = width / DEFAULT_BORDER_WIDTH_DP
        val y = height / DEFAULT_BORDER_WIDTH_DP
        val radius = x.coerceAtMost(y)

        clipPath.addCircle(x, y, radius, Path.Direction.CW)
        canvas?.clipPath(clipPath)

        super.onDraw(canvas)

        paint.apply {
            style = Paint.Style.STROKE
            color = borderColor
            strokeWidth = borderWidth
        }

        canvas?.drawCircle(x, y, radius, paint)
    }
}


