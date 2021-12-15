package com.thanh_nguyen.test_count_down.app.model

import android.graphics.drawable.Drawable

data class FeatureItemModel(
    val icon: Drawable?,
    val name: String?,
    val type: FeatureItemType
): BaseModel()

enum class FeatureItemType{
    DETAIL,
    HOROSCOPE,
}