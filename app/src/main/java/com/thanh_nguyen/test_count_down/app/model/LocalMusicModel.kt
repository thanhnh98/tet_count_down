package com.thanh_nguyen.test_count_down.app.model

import android.graphics.Bitmap

class LocalMusicModel(
    val id: Int = 0,
    var uri: String = "",
    val name: String = "",
    val title: String? = null,
    val artist: String? = null,
    val duration: Long? = null,
    val thumbnail: Bitmap? = null,
): BaseModel(){
    fun clone(): LocalMusicModel{
        return LocalMusicModel(
            this.id,
            this.uri,
            this.name,
            this.title,
            this.artist,
            this.duration,
            this.thumbnail
        )
    }
}