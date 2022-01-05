package com.thanh_nguyen.test_count_down.utils

import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics
import com.thanh_nguyen.test_count_down.App


fun getScreenWidth(): Int {
    return Resources.getSystem().displayMetrics.widthPixels
}

fun getScreenHeight(): Int {
    return Resources.getSystem().displayMetrics.heightPixels
}

/**
 * This method converts dp unit to equivalent pixels, depending on device density.
 *
 * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels
 * @param context Context to get resources and device specific display metrics
 * @return A float value to represent px equivalent to dp depending on device density
 */
fun convertDpToPixel(dp: Float): Float {
    return dp * (App.getInstance().resources
        .displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)
}

/**
 * This method converts device specific pixels to density independent pixels.
 *
 * @param px A value in px (pixels) unit. Which we need to convert into db
 * @param context Context to get resources and device specific display metrics
 * @return A float value to represent dp equivalent to px value
 */
fun convertPixelsToDp(px: Float): Float {
    return px / (App.getInstance().resources
        .displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)
}

