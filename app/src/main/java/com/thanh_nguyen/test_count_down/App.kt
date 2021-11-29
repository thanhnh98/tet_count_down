package com.thanh_nguyen.test_count_down

import android.app.Application
import android.appwidget.AppWidgetManager
import android.content.Intent
import android.content.res.Resources
import android.util.Log
import androidx.annotation.StringRes
import androidx.lifecycle.LifecycleObserver
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.thanh_nguyen.test_count_down.di.appModule
import com.thanh_nguyen.test_count_down.utils.setAlarmRemindAfterInterval
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import java.util.*

class App: Application(), LifecycleObserver, KodeinAware {
    override fun onCreate() {
        super.onCreate()
        instance = this
        MobileAds.initialize(this)
        val testDeviceIds = listOf(
            "11A5A306389981CA70B4C70CBE041154",
            "2D754340943AB2A524632B55EEC48816",
            "7B341CFFF11DF94C6789999704C2A784"
        )
        val configuration = RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build()
        MobileAds.setRequestConfiguration(configuration)
        setAlarmRemindAfterInterval(this)
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