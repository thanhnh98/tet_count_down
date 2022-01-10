package com.thanh_nguyen.test_count_down.app.model.db_entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "music")
class LocalMusicEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val uri: String,
)