package com.thanh_nguyen.test_count_down.utils

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import com.thanh_nguyen.test_count_down.App

fun createMedia(uri: Uri): MediaPlayer{
    return MediaPlayer().apply {
        setAudioAttributes(
            AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .build()
        )
        setDataSource(App.getInstance(), uri)
    }
}