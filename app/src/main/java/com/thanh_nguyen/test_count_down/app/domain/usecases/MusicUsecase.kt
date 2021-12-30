package com.thanh_nguyen.test_count_down.app.domain.usecases

import com.thanh_nguyen.test_count_down.app.domain.repositories.MusicRepository

class MusicUsecase(private val musicRepository: MusicRepository) {
    fun downloadMusic(fileUrl: String) = musicRepository.downloadMusic(fileUrl)
}