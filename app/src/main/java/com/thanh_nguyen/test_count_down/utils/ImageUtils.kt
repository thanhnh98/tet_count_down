package com.thanh_nguyen.test_count_down.utils

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.media.MediaMetadataRetriever
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.thanh_nguyen.test_count_down.R

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

fun loadImage(bm: Bitmap, imageView: ImageView){
    Glide.with(imageView.context)
        .load(bm)
        .dontAnimate()
        .into(imageView)
}

fun loadImage(drawable: Drawable?, imageView: ImageView){
    Glide.with(imageView.context)
        .load(drawable)
        .placeholder(R.drawable.music_disk)
        .dontAnimate()
        .into(imageView)
}

fun getSongCover(path: String?): ByteArray? {
    return MediaMetadataRetriever().apply {
        setDataSource(path)
    }.embeddedPicture
}