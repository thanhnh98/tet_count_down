package com.thanh_nguyen.test_count_down.app.presentation.ui.main.musics

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.thanh_nguyen.test_count_down.App
import com.thanh_nguyen.test_count_down.app.domain.usecases.MusicUsecase
import com.thanh_nguyen.test_count_down.app.model.HomeDataModel
import com.thanh_nguyen.test_count_down.common.base.mvvm.viewmodel.BaseCollectionViewModel
import com.thanh_nguyen.test_count_down.utils.cmn
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception

class ListMusicsViewModel(private val musicUsecase: MusicUsecase): BaseCollectionViewModel() {

    private var _homeData = MutableLiveData<File?>()
    val homeData: LiveData<File?> get() = _homeData

    fun downloadMusic(fileUrl: String){
        viewModelScope.launch {
            musicUsecase.downloadMusic(fileUrl).collect {
                cmn("data received: ${it.data.toString()}")
                saveToCache(
                    it.data,
                    "hpxn.mp3"
                ).apply {
                    _homeData.postValue(this)
                }
            }
        }
    }

    private val separator = File.separator
    private fun cachePath(context: Context) = "${context.cacheDir.path}${separator}musics$separator"

    private fun saveToCache(body: ResponseBody?, fileName: String, overwrite: Boolean = false, bufferSize: Int = DEFAULT_BUFFER_SIZE): File? {
        val desFile = File("${cachePath(App.getInstance())}$fileName")

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

}