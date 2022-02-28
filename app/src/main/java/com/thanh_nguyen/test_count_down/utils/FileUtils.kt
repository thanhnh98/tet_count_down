package com.thanh_nguyen.test_count_down.utils

import android.net.Uri
import androidx.annotation.RawRes
import androidx.core.net.toUri
import com.thanh_nguyen.test_count_down.App
import okhttp3.ResponseBody
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

private val separator = File.separator
private val cachePath = "${App.getInstance().cacheDir.path}${separator}musics$separator"

fun saveFileToCache(body: ResponseBody?, fileName: String, overwrite: Boolean = true, bufferSize: Int = DEFAULT_BUFFER_SIZE): File? {
    val desFile = File("${cachePath}$fileName")

    if (desFile.exists()) {
        if (!overwrite)
            throw FileAlreadyExistsException(desFile, other = desFile, reason = "The destination file already exists.")
        else if (!desFile.delete())
            throw FileAlreadyExistsException(desFile, other = desFile, reason = "Tried to overwrite the destination, but failed to delete it.")
    }

    try  {
        val input = body?.byteStream()

        desFile.parentFile?.mkdirs()
        val outputStream = FileOutputStream(desFile)
        var read: Int?

        outputStream.use { output ->
            val buffer = ByteArray(4 * 1024) // or other buffer size
            while (input?.read(buffer).also { read = it } != -1) {
                output.write(buffer, 0, read?:return@use )
            }
            output.flush()
        }
        return desFile
    }
    catch (e: Exception){

    }

    return null
}

fun findCacheByUri(uri: Uri): File?{
    val sourceFile = File(uri.path)
    val desFile = File("${cachePath}${sourceFile.name}")
    return if (desFile.exists()) desFile else null
}

fun deleteFile(uri: Uri): Boolean{
    return try {
        val sourceFile = File(uri.path)
        sourceFile.delete()
    }catch (e: Exception){
        false
    }

}

fun createFileCachedFromAsset(@RawRes id: Int, saveAsName: String): File?{
    try {
        val bufferSize: Int = DEFAULT_BUFFER_SIZE
        val desFile = File("${cachePath}${saveAsName}")

        if (findCacheByUri(desFile.toUri()) != null)
            return desFile

        if (desFile.isDirectory) {
            if (!desFile.mkdirs())
                throw FileSystemException(file = desFile, other = desFile, reason = "Failed to create target directory.")
        } else {
            desFile.parentFile?.mkdirs()

            val inputStream: InputStream = App.getInstance().resources.openRawResource(id)
            val outputStream = FileOutputStream(desFile)

            inputStream.use { input ->
                outputStream.use { output ->
                    input.copyTo(output, bufferSize)
                }

            }
        }
        return desFile
    }
    catch (e: Exception){
        e.printStackTrace()
        return null
    }
}

fun saveFileToCache(uri: Uri, bufferSize: Int = DEFAULT_BUFFER_SIZE): File? {
    try {
        val sourceFile = File(uri.path)
        val desFile = File("${cachePath}bg_music.mp3")

        if (desFile.isDirectory) {
            if (!desFile.mkdirs())
                throw FileSystemException(file = desFile, other = desFile, reason = "Failed to create target directory.")
        } else {
            desFile.parentFile?.mkdirs()

            val inputStream = App.getInstance().contentResolver.openInputStream(uri)
            val outputStream = FileOutputStream(desFile)

            inputStream.use { input ->
                outputStream.use { output ->
                    input?.copyTo(output, bufferSize)
                }

            }
        }
        CMN("${desFile.path}  - \n ${desFile.path.toUri()}")
        return desFile
    }
    catch (e: Exception){
        e.printStackTrace()
        return  null
    }
}