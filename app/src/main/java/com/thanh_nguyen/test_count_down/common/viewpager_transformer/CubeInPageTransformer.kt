package com.thanh_nguyen.test_count_down.common.viewpager_transformer

import android.view.View
import androidx.viewpager2.widget.ViewPager2


class CubeInPageTransformer : ViewPager2.PageTransformer {
    private val ROTATION = -15f

    override fun transformPage(page: View, pos: Float) {
        val width = page.width.toFloat()
        val height = page.height.toFloat()
        val rotation = ROTATION * pos * -1.25f
        page.pivotX = width * 0.5f
        page.pivotY = height
        page.rotation = rotation
    }
}