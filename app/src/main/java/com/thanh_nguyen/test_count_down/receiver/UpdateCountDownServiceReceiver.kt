package com.thanh_nguyen.test_count_down.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import com.thanh_nguyen.test_count_down.external.firebase.AppAnalytics
import com.thanh_nguyen.test_count_down.service.CountDownForegroundService
import com.thanh_nguyen.test_count_down.utils.cmn

class UpdateCountDownServiceReceiver: BroadcastReceiver() {
    companion object {
        const val RECEIVER_EVENT = "ReceiverEvent"
    }
    override fun onReceive(context: Context?, intent: Intent?) {
        val event = intent?.extras?.getSerializable(RECEIVER_EVENT) as ReceiverEvent?
        when (event) {
            ReceiverEvent.CLOSE_COUNT_DOWN -> {
                AppAnalytics.trackCloseCountDownNotification()
                context?.stopService(Intent(context, CountDownForegroundService::class.java))
            }
            ReceiverEvent.PINNED_COUNT_DOWN -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    AppAnalytics.trackPinnedNotification()
                    context?.startForegroundService(Intent(context, CountDownForegroundService::class.java))
                }
            }
        }
    }

}