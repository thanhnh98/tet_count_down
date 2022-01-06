package com.thanh_nguyen.test_count_down.app.data.data_source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.thanh_nguyen.test_count_down.app.data.data_source.local.room.dao.LocalMusicDao
import com.thanh_nguyen.test_count_down.app.model.db_entity.LocalMusicEntity

@Database(version = 1, entities = [LocalMusicEntity::class])
abstract class AppRoomDB: RoomDatabase() {
    abstract fun localMusicDao(): LocalMusicDao
    companion object {
        private var instance: AppRoomDB? = null

        operator fun invoke(context: Context): AppRoomDB {
            return instance?: synchronized(Any()) {
                instance?: Room.databaseBuilder(
                    context,
                    AppRoomDB::class.java, "SapTet.db"
                ).build().also {
                    instance = it
                }
            }

        }
    }
}