package com.thanh_nguyen.test_count_down.di

import com.thanh_nguyen.test_count_down.app.data.network.ApiClient
import com.thanh_nguyen.test_count_down.app.data.service.AdsService
import com.thanh_nguyen.test_count_down.app.data.service.EventService
import com.thanh_nguyen.test_count_down.app.data.service.MusicService
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

const val SERVICE_MODULE = "SERVICE_MODULE"
val serviceModule = Kodein.Module(SERVICE_MODULE, false){
    bind() from singleton {
        ApiClient.createService<EventService>()
    }

    bind() from singleton {
        ApiClient.createService<AdsService>()
    }

    bind() from singleton {
        ApiClient.createService<MusicService>()
    }
}