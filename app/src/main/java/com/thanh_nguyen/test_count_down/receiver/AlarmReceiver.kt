package com.thanh_nguyen.test_count_down.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.thanh_nguyen.test_count_down.common.notification.pushNotification

class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        Log.e("received","data")
        pushNotification(p0?:return)
    }
}