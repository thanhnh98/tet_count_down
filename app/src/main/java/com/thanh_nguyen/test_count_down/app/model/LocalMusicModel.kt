package com.thanh_nguyen.test_count_down.app.model

import android.graphics.Bitmap

class LocalMusicModel(
    val id: Int = 0,
    val uri: String = "",
    val name: String = "",
    val title: String? = null,
    val artist: String? = null,
    val duration: Long? = null,
    val thumbnail: Bitmap? = null
): BaseModel()