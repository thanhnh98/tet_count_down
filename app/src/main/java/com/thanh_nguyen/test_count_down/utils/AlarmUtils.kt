package com.thanh_nguyen.test_count_down.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.annotation.IntDef
import androidx.appcompat.app.AppCompatActivity
import com.thanh_nguyen.test_count_down.App
import com.thanh_nguyen.test_count_down.receiver.AlarmReceiver
import java.util.*

internal val alarmManager: AlarmManager = App.getInstance().getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager

fun setAlarmRemindAfterInterval(context: Context, interval: Long = AlarmManager.INTERVAL_DAY){
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.HOUR_OF_DAY, 7)
    calendar.set(Calendar.MINUTE, 0)
    val timeMillis = calendar.timeInMillis
    alarmManager.setRepeating(
        AlarmManager.RTC_WAKEUP,
        timeMillis,
        interval,
        createAlarmPendingIntent(context)
    )
}

private fun createAlarmPendingIntent(context: Context): PendingIntent?{
    val intentAlarm = Intent(context, AlarmReceiver::class.java)
    return PendingIntent.getBroadcast(
        context,
        999,
        intentAlarm,
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_CANCEL_CURRENT
    )
}

fun closeAlarm(context: Context){
    alarmManager.cancel(createAlarmPendingIntent(context))
}