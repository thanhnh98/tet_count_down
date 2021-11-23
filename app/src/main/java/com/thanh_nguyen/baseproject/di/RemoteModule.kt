package com.thanh_nguyen.baseproject.di

import com.thanh_nguyen.baseproject.app.data.data_source.remote.LoginRemoteDataSource
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

/**
 * module for remote dependencies
 */

const val REMOTE_MODULE = "remote_module"

val remoteModule = Kodein.Module(REMOTE_MODULE, false){
    bind() from provider {
        LoginRemoteDataSource(instance())
    }
}