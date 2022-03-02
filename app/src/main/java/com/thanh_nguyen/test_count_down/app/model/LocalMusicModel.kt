package com.thanh_nguyen.test_count_down.app.model

import android.graphics.Bitmap
import com.thanh_nguyen.test_count_down.utils.decodeBase64

class LocalMusicModel(
    val id: Int = 0,
    var uri: String = "",
    val name: String = "",
    val title: String? = null,
    val artist: String? = null,
    val duration: Long? = null,
    val thumbnailBase64: String? = null,
): BaseModel(){
    fun clone(): LocalMusicModel{
        return LocalMusicModel(
            this.id,
            this.uri,
            this.name,
            this.title,
            this.artist,
            this.duration,
            this.thumbnailBase64
        )
    }

    fun equals(other: LocalMusicModel): Boolean {
        return this.name == other.name && this.title == other.title && this.artist == other.artist
    }

    fun getThumbnailBitmap(): Bitmap? {
        return decodeBase64(thumbnailBase64)
    }

}