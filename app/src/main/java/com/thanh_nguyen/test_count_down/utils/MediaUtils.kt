package com.thanh_nguyen.test_count_down.utils

import android.content.ContentUris
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Size
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentResolverCompat
import com.thanh_nguyen.test_count_down.App
import com.thanh_nguyen.test_count_down.app.model.LocalMusicModel
import com.thanh_nguyen.test_count_down.provider.AppProvider
import java.lang.Exception


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

suspend fun getAllMusic(): List<LocalMusicModel>{
    val selection: String = MediaStore.Audio.Media.IS_MUSIC.toString() + " != 0"

    val projection = arrayOf(
        MediaStore.Audio.Media._ID,
        MediaStore.Audio.Media.ARTIST,
        MediaStore.Audio.Media.TITLE,
        MediaStore.Audio.Media.DISPLAY_NAME,
        MediaStore.Audio.Media.DURATION,
    )
    val sortOrder = "${MediaStore.Audio.Media.DISPLAY_NAME} ASC"

    val cursor = ContentResolverCompat.query(
        AppProvider.getContentResolver(),
        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
        projection,
        selection,
        null,
        sortOrder,
         null
    )

    val musics: MutableList<LocalMusicModel> = ArrayList()

    cursor?.use { cursor ->
        // Cache column indices.
        val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
        val artistColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
        val titleColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
        val displayNameColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)
        val durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)

        while (cursor.moveToNext()) {

            val id = cursor.getLong(idColumn)
            val artist = cursor.getString(artistColumn)
            val title = cursor.getString(titleColumn)
            val displayName = cursor.getString(displayNameColumn)
            val duration = cursor.getLong(durationColumn)

            val contentUri: Uri = ContentUris.withAppendedId(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                id
            )

            val thumbnail = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                try{
                    AppProvider.getContentResolver().loadThumbnail(
                        contentUri,
                        Size(50, 50),
                        null
                    )
                }
                catch (e: Exception){
                    null
                }
            } else {
                null
            }

            CMN("$id: $displayName $artist $title $duration $contentUri $thumbnail ")
            musics.add(
                LocalMusicModel(
                    uri = contentUri.toString(),
                    name = displayName,
                    title = title,
                    artist = artist,
                    duration = duration,
                    thumbnail = thumbnail
                )
            )
        }
    }
    return musics.filter {
        it.uri.isNotEmpty() && it.name.contains(".mp3") && it.duration?:0 > 0
    }
}