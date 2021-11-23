package com.thanh_nguyen.baseproject.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.thanh_nguyen.baseproject.R

fun loadImage(url: String, imageView: ImageView){
    Glide.with(imageView.context)
        .load(url)
        .centerCrop()
        .placeholder(R.drawable.img_avatar)
        .into(imageView)
}