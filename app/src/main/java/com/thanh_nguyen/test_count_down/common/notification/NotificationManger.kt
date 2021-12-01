package com.thanh_nguyen.test_count_down.common.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.PRIORITY_MAX
import com.thanh_nguyen.test_count_down.BuildConfig
import com.thanh_nguyen.test_count_down.R
import com.thanh_nguyen.test_count_down.app.model.DateDataModel
import com.thanh_nguyen.test_count_down.app.model.HomeDataModel
import com.thanh_nguyen.test_count_down.app.presentation.ui.SplashScreen
import com.thanh_nguyen.test_count_down.common.Constants
import com.thanh_nguyen.test_count_down.receiver.ReceiverEvent
import com.thanh_nguyen.test_count_down.receiver.UpdateCountDownServiceReceiver
import com.thanh_nguyen.test_count_down.utils.formatTwoNumber
import com.thanh_nguyen.test_count_down.utils.getDaysUntilDate
import com.thanh_nguyen.test_count_down.utils.getSecondsUntilDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay

fun pushNotification(context: Context, notificationId: Int, notification: Notification){
    val notificationManager: NotificationManager? = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
    notificationManager?.notify(notificationId, notification)
}

fun createNotification(context: Context, view: RemoteViews, requestCode: Int, channelId: String, isOnGoing: Boolean = false): Notification{

    val notificationIntent = Intent(context, SplashScreen::class.java)
    val notificationManager: NotificationManager? = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

    notificationIntent.flags = (Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)

    val intent = PendingIntent.getActivity(
        context,
        requestCode,
        notificationIntent,
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    )

    val notificationBuilder = NotificationCompat.Builder(context, "TET")
        .setSmallIcon(R.mipmap.ic_launcher)
        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
        .setCustomContentView(view)
        .setVibrate(null)
        .setContentIntent(intent)
        .setOngoing(true)
        .setPriority(PRIORITY_MAX)
        .setProgress(100,50, false)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
        val channel = NotificationChannel(
            channelId,
            "Count Down",
            NotificationManager.IMPORTANCE_LOW
        )
        notificationManager?.createNotificationChannel(channel)
        notificationBuilder.setChannelId(channelId)
    }

    val notification = notificationBuilder.build()

    if (!isOnGoing)
        notification.flags = Notification.FLAG_ONGOING_EVENT

    return notification
}

fun createNotificationCountdownView(context: Context): RemoteViews{
    val minuteSec = 60
    val hourSec = minuteSec * 60
    val daySec = hourSec * 24
    val totalSeconds = getSecondsUntilDate(Constants.EventDate.LUNAR_NEW_YEAR)
    val days = totalSeconds /  daySec
    val hours = (totalSeconds - days * daySec) / hourSec
    val minutes = (totalSeconds - days * daySec - hours * hourSec) / minuteSec
    val seconds = totalSeconds - days * daySec - hours * hourSec - minutes * minuteSec

    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.notification_layout)
    views.setOnClickPendingIntent(R.id.layout_root,
        PendingIntent.getActivity(
            context,
            0,
            Intent(
                context, SplashScreen::class.java),
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_CANCEL_CURRENT
        )
    )

    val dayText = days.formatTwoNumber()
    val hourText = hours.formatTwoNumber()
    val minuteText = minutes.formatTwoNumber()
    val secondText = seconds.formatTwoNumber()

    views.setTextViewText(R.id.tv_day_1, dayText.subSequence(0,1))
    views.setTextViewText(R.id.tv_day_2, dayText.subSequence(1,2))

    views.setTextViewText(R.id.tv_hour_1, hourText.subSequence(0,1))
    views.setTextViewText(R.id.tv_hour_2, hourText.subSequence(1,2))

    views.setTextViewText(R.id.tv_minute_1, minuteText.subSequence(0,1))
    views.setTextViewText(R.id.tv_minute_2, minuteText.subSequence(1,2))

    views.setTextViewText(R.id.tv_second_1, secondText.subSequence(0,1))
    views.setTextViewText(R.id.tv_second_2, secondText.subSequence(1,2))

    views.setOnClickPendingIntent(R.id.img_close, PendingIntent.getBroadcast(
        context,
        999,
        Intent(context, UpdateCountDownServiceReceiver::class.java).apply {
            putExtra("ReceiverEvent", ReceiverEvent.CLOSE_FOREGROUND )
        },
        PendingIntent.FLAG_IMMUTABLE
    ))

    return views
}