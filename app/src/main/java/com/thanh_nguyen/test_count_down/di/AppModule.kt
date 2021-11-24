package com.thanh_nguyen.test_count_down.di

import org.kodein.di.Kodein

const val APP_MODULE = "app_module"

val appModule = Kodein.Module(APP_MODULE, false){
    import(serviceModule)
    import(remoteModule)
    import(repositoryModule)
    import(viewModelModule)
    import(useCaseModule)
    import(othersModule)
}