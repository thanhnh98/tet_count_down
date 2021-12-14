package com.thanh_nguyen.test_count_down.service

import android.util.Log
import com.thanh_nguyen.test_count_down.app.data.data_source.local.AppSharedPreferences
import com.thanh_nguyen.test_count_down.common.notification.createNotificationCountdownViewAlive
import com.thanh_nguyen.test_count_down.common.notification.createNotificationKeepAlive
import com.thanh_nguyen.test_count_down.external.firebase.AppAnalytics
import com.thanh_nguyen.test_count_down.utils.closeAlarm
import com.thanh_nguyen.test_count_down.utils.cmn
import com.thanh_nguyen.test_count_down.utils.setAlarmRemindAfterInterval
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect


class CountDownForegroundService: BaseService() {
    companion object {
        const val FOREGROUND_ID = 111
        const val FOREGROUND_REQUEST_CODE = 1998
        const val FOREGROUND_NOTI_CHANNEL = "CountDown"
    }

    override fun onCreate() {
        super.onCreate()
        closeAlarm(this)
        observeEvent {
            AppSharedPreferences.setisClosedCountDownNoti(false)
            while (true){
                try {
                    startForeground(
                        FOREGROUND_ID,
                        createNotificationKeepAlive(
                            this@CountDownForegroundService,
                            createNotificationCountdownViewAlive(context = this@CountDownForegroundService),
                            FOREGROUND_REQUEST_CODE,
                            FOREGROUND_NOTI_CHANNEL
                        )
                    )
                }catch (e: Exception){
                    e.printStackTrace()
                }
                delay(1000)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        setAlarmRemindAfterInterval(this)
        CoroutineScope(Dispatchers.IO).launch {
            AppSharedPreferences.setisClosedCountDownNoti(true)
            cancel()
        }
    }
}