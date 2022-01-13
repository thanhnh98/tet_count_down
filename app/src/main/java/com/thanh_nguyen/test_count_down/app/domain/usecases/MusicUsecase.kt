package com.thanh_nguyen.test_count_down.app.domain.usecases

import com.thanh_nguyen.test_count_down.app.domain.repositories.MusicRepository
import com.thanh_nguyen.test_count_down.app.model.LocalMusicModel

class MusicUsecase(private val musicRepository: MusicRepository) {
    fun downloadMusic(fileUrl: String) = musicRepository.downloadMusic(fileUrl)
    fun getListMusics() = musicRepository.getListMusics()
    suspend fun getListMusicsLocal() = musicRepository.getListMusicsLocal()
    suspend fun addMusic(localMusicModel: LocalMusicModel) = musicRepository.addMusic(localMusicModel)
    suspend fun deleteMusic(localMusicModel: LocalMusicModel) = musicRepository.deleteMusic(localMusicModel)
}