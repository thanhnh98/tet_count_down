package com.thanh_nguyen.test_count_down.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

open class BaseService: Service() {
    var job: Job? = null
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
    }

    fun observeEvent(invoker: (suspend () -> Unit)){
        job = CoroutineScope(Dispatchers.Main).launch{
            invoker.invoke()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()
    }

}