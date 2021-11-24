package com.thanh_nguyen.baseproject.di

import com.thanh_nguyen.baseproject.app.domain.usecases.EventUseCase
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

const val USE_CASE_MODULE = "USE_CASE_MODULE"
val useCaseModule = Kodein.Module(USE_CASE_MODULE, false){
    bind() from provider {
        EventUseCase(instance())
    }
}