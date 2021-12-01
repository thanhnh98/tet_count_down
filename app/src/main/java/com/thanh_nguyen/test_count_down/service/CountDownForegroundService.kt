package com.thanh_nguyen.test_count_down.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.thanh_nguyen.test_count_down.common.notification.createNotification
import com.thanh_nguyen.test_count_down.common.notification.createNotificationCountdownView
import kotlinx.coroutines.*


class CountDownForegroundService: Service() {
    companion object {
        const val FOREGROUND_ID = 111
        const val FOREGROUND_REQUEST_CODE = 1998
        const val FOREGROUND_NOTI_CHANNEL = "CountDown"
    }
    private var job: Job? = null
    override fun onCreate() {
        super.onCreate()
        job = CoroutineScope(Dispatchers.IO).launch {
            while (true){
                startForeground(FOREGROUND_ID, createNotification(
                    this@CountDownForegroundService,
                    createNotificationCountdownView(context = this@CountDownForegroundService),
                    FOREGROUND_REQUEST_CODE,
                    FOREGROUND_NOTI_CHANNEL))
                delay(1000)
            }
        }
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()
    }
}