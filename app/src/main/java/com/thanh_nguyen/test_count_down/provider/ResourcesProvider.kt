package com.thanh_nguyen.test_count_down.provider

import androidx.annotation.ColorInt
import com.thanh_nguyen.test_count_down.App
import com.thanh_nguyen.test_count_down.utils.getColorCompat

class AppResourcesProvider{

   companion object {
       @ColorInt
       fun getColor(resId: Int) = App.getInstance().getColorCompat(resId)

       fun getString(resId: Int) = App.getInstance().getString(resId)

       fun getString(resId: Int, vararg args: Any) = App.getInstance().getString(resId, *args)

       fun getDimen(resId: Int): Float = App.getInstance().resources.getDimension(resId)

       fun getDimenInt(resId: Int) = App.getInstance().resources.getDimensionPixelSize(resId)
   }
}