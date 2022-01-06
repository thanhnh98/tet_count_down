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
    suspend fun addMusic(localMusicEntity: LocalMusicEntity): Flow<Boolean>

    @Query("select * from music")
    suspend fun getListMusics(): Flow<List<LocalMusicEntity>>

}