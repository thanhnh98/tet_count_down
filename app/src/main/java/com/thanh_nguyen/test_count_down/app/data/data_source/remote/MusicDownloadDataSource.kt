package com.thanh_nguyen.test_count_down.app.data.data_source.remote

import com.thanh_nguyen.test_count_down.app.data.service.MusicService

class MusicDownloadDataSource(private val service: MusicService): BaseRemoteDataSource() {
    fun downloadMusic(fileUrl: String) = getResult {
        service.downloadMusic(fileUrl)
    }
}