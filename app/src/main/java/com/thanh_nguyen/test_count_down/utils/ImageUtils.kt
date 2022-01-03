package com.thanh_nguyen.test_count_down.utils

import android.media.MediaMetadataRetriever
import android.widget.ImageView
import com.bumptech.glide.Glide

fun loadImage(url: String, imageView: ImageView){
    Glide.with(imageView.context)
        .load(url)
        .centerCrop()
        .into(imageView)
}

fun loadImage(byte: ByteArray, imageView: ImageView){
    Glide.with(imageView.context)
        .load(byte)
        .dontAnimate()
        .into(imageView)
}

fun getSongCover(path: String?): ByteArray? {
    return MediaMetadataRetriever().apply {
        setDataSource(path)
    }.embeddedPicture
}