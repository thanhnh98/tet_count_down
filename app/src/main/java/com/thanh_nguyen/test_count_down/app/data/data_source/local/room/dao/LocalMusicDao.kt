package com.thanh_nguyen.test_count_down.app.data.data_source.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.thanh_nguyen.test_count_down.app.model.db_entity.LocalMusicEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalMusicDao {
    @Insert(onConflict = REPLACE)
    suspend fun addMusic(localMusicEntity: LocalMusicEntity)

    @Query("select * from music")
    suspend fun getListMusics(): List<LocalMusicEntity>

    @Query("delete from music where id = :id")
    suspend fun deleteMusic(id: Int)

}