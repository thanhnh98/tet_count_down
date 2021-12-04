package com.thanh_nguyen.test_count_down.app.model

import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes

data class AboutItemDataModel(
   val title: String,
   val content: String,
   val imgDrawable: Drawable? = null,
   val imgSource: String? = null
): BaseModel()
