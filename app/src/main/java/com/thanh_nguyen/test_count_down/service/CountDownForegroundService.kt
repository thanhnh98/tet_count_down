package com.thanh_nguyen.test_count_down.service

import android.content.Intent
import com.thanh_nguyen.test_count_down.R
import com.thanh_nguyen.test_count_down.app.data.data_source.local.AppSharedPreferences
import com.thanh_nguyen.test_count_down.common.notification.createNotificationCountdownViewAlive
import com.thanh_nguyen.test_count_down.common.notification.createNotificationKeepAlive
import com.thanh_nguyen.test_count_down.provider.AppProvider
import com.thanh_nguyen.test_count_down.utils.*
import kotlinx.coroutines.*


class CountDownForegroundService: BaseService() {
    companion object {
        const val FOREGROUND_ID = 111
        const val FOREGROUND_REQUEST_CODE = 1998
        const val FOREGROUND_NOTI_CHANNEL = "CountDown"
    }

    override fun onCreate() {
        super.onCreate()
        closeAlarm(this)
        val currentService = Intent(this, CountDownForegroundService::class.java)
        observeEvent {
            if (isTetOnGoing()) {
                with(Dispatchers.IO) {
                    try {
                        stopService(currentService)
                        AppSharedPreferences.setIsEnableCountDownNoti(false)
                    } catch (e: Exception) {
                        WTF("failed: ${e.message}")
                    }
                }
            } else {
                showToastMessage(AppProvider.getString(R.string.toast_msg_turn_on_count_down))
                with(Dispatchers.IO) {
                    while (true) {
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
                            AppSharedPreferences.setIsEnableCountDownNoti(true)
                        } catch (e: Exception) {
                            stopService(currentService)
                            e.printStackTrace()
                        }
                        delay(1000)
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        setAlarmRemindAfterInterval(this)
        showToastMessage(AppProvider.getString(R.string.toast_msg_turn_off_count_down))
        CoroutineScope(Dispatchers.IO).launch {
            AppSharedPreferences.setIsEnableCountDownNoti(false)
            cancel()
        }
    }
}