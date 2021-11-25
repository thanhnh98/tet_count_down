package com.thanh_nguyen.test_count_down

import android.app.Application
import android.content.res.Resources
import androidx.annotation.StringRes
import androidx.lifecycle.LifecycleObserver
import com.google.android.gms.ads.MobileAds
import com.thanh_nguyen.test_count_down.di.appModule
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule

class App: Application(), LifecycleObserver, KodeinAware {
    override fun onCreate() {
        super.onCreate()
        instance = this
        MobileAds.initialize(this)
    }

    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@App))
        import(appModule)
    }

    companion object{
        @Volatile
        private var instance: App? = null

        @JvmStatic
        fun getInstance(): App = instance ?: synchronized(this) {
            instance ?: App().also {
                instance = it
            }
        }

        fun getString(@StringRes strId: Int): String {
            return getResources().getString(strId)
        }

        fun getResources(): Resources {
            return getInstance().resources
        }
    }
}