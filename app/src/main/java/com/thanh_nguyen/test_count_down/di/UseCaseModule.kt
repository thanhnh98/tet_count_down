package com.thanh_nguyen.test_count_down.di

import com.thanh_nguyen.test_count_down.app.domain.usecases.AdsUseCase
import com.thanh_nguyen.test_count_down.app.domain.usecases.EventUseCase
import com.thanh_nguyen.test_count_down.app.domain.usecases.MusicUsecase
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

const val USE_CASE_MODULE = "USE_CASE_MODULE"
val useCaseModule = Kodein.Module(USE_CASE_MODULE, false){
    bind() from provider {
        EventUseCase(instance())
    }
    bind() from provider {
        AdsUseCase(instance())
    }
    bind() from provider {
        MusicUsecase(instance())
    }
}