package com.thanh_nguyen.baseproject.common.view

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import androidx.annotation.Nullable
import androidx.appcompat.widget.AppCompatImageView
import com.thanh_nguyen.baseproject.R

class RoundCornerImageView : AppCompatImageView {
    private var radius = 15.0f
    private var path: Path = Path()

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, @Nullable attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(
        context: Context,
        @Nullable attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        val typedArray: TypedArray =
            context.obtainStyledAttributes(attrs, R.styleable.RoundCornerImageView)
        radius = typedArray.getFloat(
            R.styleable.RoundCornerImageView_corner_radius,
            15.0f
        )
    }

    private fun init(context: Context) {
    }

    override fun onDraw(canvas: Canvas) {
        val rect: RectF = RectF(0f, 0f, this.width.toFloat(), this.height.toFloat())
        path.addRoundRect(rect, radius, radius, Path.Direction.CCW)
        try {
            canvas.clipPath(path?:return)
        } catch (exception: UnsupportedOperationException) {
            setLayerType(LAYER_TYPE_SOFTWARE, null)
            try {
                canvas.clipPath(path)
            } catch (exception2: UnsupportedOperationException) {
                // shouldn't happen, but just in case
            }
        }
        super.onDraw(canvas)
    }
}