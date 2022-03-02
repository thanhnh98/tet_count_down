package com.thanh_nguyen.test_count_down.binding_adapter

import android.view.View
import androidx.databinding.BindingAdapter
import com.thanh_nguyen.test_count_down.utils.fadeInAppearance

@BindingAdapter("fadeInAppearance")
fun fadeInAppearance(view: View, alpha: Float){
    view.fadeInAppearance(alpha = alpha)
}