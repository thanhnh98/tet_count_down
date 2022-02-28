package com.thanh_nguyen.test_count_down.common.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.RectF
import android.graphics.Region
import android.util.AttributeSet
import androidx.annotation.Nullable
import androidx.appcompat.widget.AppCompatImageView

class AutoRotateCircleImageView: AppCompatImageView {
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

    fun init(context: Context, attrs: AttributeSet? = null) {

    }

    override fun onDraw(canvas: Canvas?) {
        val radius = height.coerceAtMost(width)/2f
        path.addCircle( radius, radius, radius, Path.Direction.CCW)
        try {
            canvas?.clipPath(path?:return)
        } catch (exception: UnsupportedOperationException) {
            setLayerType(LAYER_TYPE_SOFTWARE, null)
            try {
                canvas?.clipPath(path)
            } catch (exception2: UnsupportedOperationException) {
                // shouldn't happen, but just in case
            }
        }
        super.onDraw(canvas)
    }
}