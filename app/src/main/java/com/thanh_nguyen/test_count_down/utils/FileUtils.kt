package com.thanh_nguyen.test_count_down.utils

import android.content.Context
import com.thanh_nguyen.test_count_down.App
import okhttp3.ResponseBody
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

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

fun File.saveFileToCache(overwrite: Boolean = true, bufferSize: Int = DEFAULT_BUFFER_SIZE): File {
    val desFile = File("${cachePath}${this.name}")

    if (!this.exists()) {
        throw NoSuchFileException(file = this, reason = "The source file doesn't exist.")
    }

    if (desFile.exists()) {
        if (!overwrite)
            throw FileAlreadyExistsException(file = this, other = desFile, reason = "The destination file already exists.")
        else if (!this.delete())
            throw FileAlreadyExistsException(file = this, other = desFile, reason = "Tried to overwrite the destination, but failed to delete it.")
    }

    if (this.isDirectory) {
        if (!desFile.mkdirs())
            throw FileSystemException(file = this, other = desFile, reason = "Failed to create target directory.")
    } else {
        desFile.parentFile?.mkdirs()

        val inputStream = FileInputStream(this)
        val outputStream = FileOutputStream(desFile)

        inputStream.use { input ->
            outputStream.use { output ->
                input.copyTo(output, bufferSize)
            }

        }
    }

    return desFile
}