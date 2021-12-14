package com.thanh_nguyen.test_count_down.di

import com.thanh_nguyen.test_count_down.app.data.repository.ads.AdsRepositoryImpl
import com.thanh_nguyen.test_count_down.app.data.repository.event.EventRepositoryDecorator
import com.thanh_nguyen.test_count_down.app.data.repository.event.EventRepositoryImpl
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton


/**
 * module for repository dependencies
 */

const val REPO_MODULE = "repo_module"

val repositoryModule = Kodein.Module(REPO_MODULE, false){
    bind() from singleton {
        EventRepositoryDecorator(
            EventRepositoryImpl(
                instance()
            )
        )
    }

    bind() from singleton {
        AdsRepositoryImpl(
            instance()
        )
    }
}
