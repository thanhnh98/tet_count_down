package com.thanh_nguyen.test_count_down.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.thanh_nguyen.test_count_down.receiver.AlarmReceiver
import java.util.*


fun setAlarmRemindAfterInterval(context: Context){
    val intentAlarm = Intent(context, AlarmReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(
        context,
        999,
        intentAlarm,
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    )
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.HOUR_OF_DAY, 7)
    calendar.set(Calendar.MINUTE, 0)

    val alarmManager: AlarmManager = context.getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager
    alarmManager.setRepeating(
        AlarmManager.RTC_WAKEUP,
        calendar.timeInMillis,
        AlarmManager.INTERVAL_DAY,
        pendingIntent
    )
}