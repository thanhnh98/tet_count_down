package com.thanh_nguyen.baseproject.di

import com.thanh_nguyen.baseproject.app.data.network.ApiClient
import com.thanh_nguyen.baseproject.app.data.service.LoginService
import com.thanh_nguyen.baseproject.app.domain.usecases.LoginUseCase
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

const val USE_CASE_MODULE = "USE_CASE_MODULE"
val useCaseModule = Kodein.Module(USE_CASE_MODULE, false){
    bind() from provider {
        LoginUseCase(instance())
    }
}