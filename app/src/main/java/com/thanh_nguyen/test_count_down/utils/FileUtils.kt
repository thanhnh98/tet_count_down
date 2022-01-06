package com.thanh_nguyen.test_count_down.utils

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
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

fun saveFileToCache(uri: Uri, overwrite: Boolean = true, bufferSize: Int = DEFAULT_BUFFER_SIZE): File? {
    try {
        val sourceFile = File(uri.path)
        val desFile = File("${cachePath}${sourceFile.name}")

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
        cmn("${desFile.path}  - \n ${desFile.path.toUri()}")
        return desFile
    }
    catch (e: Exception){
        e.printStackTrace()
        return  null
    }
}