package com.thanh_nguyen.test_count_down.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.thanh_nguyen.test_count_down.service.CountDownForegroundService
import com.thanh_nguyen.test_count_down.utils.cmn

class UpdateCountDownServiceReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val eventReceiver = intent?.extras?.getSerializable("ReceiverEvent") as ReceiverEvent?
        cmn("zô chưa ${eventReceiver}")
        when(eventReceiver){
            is ReceiverEvent -> {
                context?.stopService(Intent(context, CountDownForegroundService::class.java))
            }
        }
    }

}