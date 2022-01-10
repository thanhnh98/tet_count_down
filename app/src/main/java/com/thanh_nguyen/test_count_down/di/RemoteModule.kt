package com.thanh_nguyen.test_count_down.di

import com.thanh_nguyen.test_count_down.app.data.data_source.local.LocalMusicDataSource
import com.thanh_nguyen.test_count_down.app.data.data_source.local.room.AppRoomDB
import com.thanh_nguyen.test_count_down.app.data.data_source.local.room.AppRoomDB_Impl
import com.thanh_nguyen.test_count_down.app.data.data_source.local.room.dao.LocalMusicDao
import com.thanh_nguyen.test_count_down.app.data.data_source.local.room.dao.LocalMusicDao_Impl
import com.thanh_nguyen.test_count_down.app.data.data_source.remote.AdsRemoteDataSource
import com.thanh_nguyen.test_count_down.app.data.data_source.remote.EventRemoteDataSource
import com.thanh_nguyen.test_count_down.app.data.data_source.remote.MusicDownloadDataSource
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

/**
 * module for remote dependencies
 */

const val REMOTE_MODULE = "remote_module"

val remoteModule = Kodein.Module(REMOTE_MODULE, false){
    bind() from singleton {
        EventRemoteDataSource(instance())
    }
    bind() from singleton {
        AdsRemoteDataSource(instance())
    }
    bind() from singleton {
        MusicDownloadDataSource(instance())
    }

    bind() from singleton {
        LocalMusicDataSource(
            getLocalMusicDao()
        )
    }
}

fun getLocalMusicDao(): LocalMusicDao? {
    return AppRoomDB.instance?.localMusicDao()
}
