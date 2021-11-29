package com.thanh_nguyen.test_count_down.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.thanh_nguyen.test_count_down.receiver.AlarmReceiver

fun setAlarmRemindAfterInterval(context: Context, interval: Long = 18 * 60 * 60 * 1000){
    val intentAlarm = Intent(context, AlarmReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(context, 999, intentAlarm, PendingIntent.FLAG_CANCEL_CURRENT)
    val alarmManager: AlarmManager = context.getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager
    alarmManager.setRepeating(
        AlarmManager.RTC_WAKEUP,
        System.currentTimeMillis(),
        interval,
        pendingIntent
    )
}