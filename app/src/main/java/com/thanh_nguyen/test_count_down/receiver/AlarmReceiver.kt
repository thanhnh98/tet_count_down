package com.thanh_nguyen.test_count_down.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.thanh_nguyen.test_count_down.common.notification.pushAlertNotification
import com.thanh_nguyen.test_count_down.utils.cmn
import com.thanh_nguyen.test_count_down.utils.getDaysUntilDate

class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, p1: Intent?) {
        if (context == null)
            return

        if (getDaysUntilDate() < 99)
            pushAlertNotification(context)
    }
}