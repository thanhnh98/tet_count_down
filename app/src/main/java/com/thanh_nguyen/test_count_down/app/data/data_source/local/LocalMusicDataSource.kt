package com.thanh_nguyen.test_count_down.app.data.data_source.local

import com.thanh_nguyen.test_count_down.app.data.data_source.local.room.dao.LocalMusicDao
import com.thanh_nguyen.test_count_down.app.model.ListMusicModel
import com.thanh_nguyen.test_count_down.app.model.LocalMusicModel
import com.thanh_nguyen.test_count_down.app.model.db_entity.LocalMusicEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalMusicDataSource(
    private val dao: LocalMusicDao?
) {
    suspend fun getListMusicsLocal() = dao?.getListMusics()?.map { musicLocalEntity ->
        LocalMusicModel(
            id = musicLocalEntity?.id,
            name = musicLocalEntity.name,
            uri = musicLocalEntity.uri
        )
    }

    suspend fun addMusic(localMusic: LocalMusicModel){
        dao?.addMusic(
            LocalMusicEntity(
                name = localMusic.name,
                uri = localMusic.uri
            )
        )
    }

    suspend fun deleteMusic(localMusic: LocalMusicModel){
        dao?.deleteMusic(localMusic.id)
    }
}