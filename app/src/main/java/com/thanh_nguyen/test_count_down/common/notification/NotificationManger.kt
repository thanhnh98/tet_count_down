package com.thanh_nguyen.test_count_down.common.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.PRIORITY_MAX
import com.thanh_nguyen.test_count_down.R
import com.thanh_nguyen.test_count_down.app.presentation.ui.GetStartedScreen
import com.thanh_nguyen.test_count_down.common.Constants
import com.thanh_nguyen.test_count_down.provider.AppResourcesProvider
import com.thanh_nguyen.test_count_down.receiver.ReceiverEvent
import com.thanh_nguyen.test_count_down.receiver.UpdateCountDownServiceReceiver
import com.thanh_nguyen.test_count_down.receiver.UpdateCountDownServiceReceiver.Companion.RECEIVER_EVENT
import com.thanh_nguyen.test_count_down.service.CountDownForegroundService.Companion.FOREGROUND_ID
import com.thanh_nguyen.test_count_down.utils.formatTwoNumber
import com.thanh_nguyen.test_count_down.utils.getDaysUntilDate
import com.thanh_nguyen.test_count_down.utils.getSecondsUntilDate
import com.thanh_nguyen.test_count_down.utils.isTetOnGoing

fun createNotificationKeepAlive(
    context: Context,
    view: RemoteViews,
    requestCode: Int,
    channelId: String,
    isOnGoing: Boolean = false,
): Notification{

    val notificationIntent = Intent(context, GetStartedScreen::class.java)
    val notificationManager: NotificationManager? = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

    notificationIntent.flags = (Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)

    val intent = PendingIntent.getActivity(
        context,
        requestCode,
        notificationIntent,
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    )

    val notificationBuilder = NotificationCompat.Builder(context, "TET")
        .setSmallIcon(R.drawable.ic_notification)
        .setColor(AppResourcesProvider.getColor(R.color.colorPrimaryNotification))
        .setCustomBigContentView(view)
        .setCustomContentView(view)
        .setVibrate(null)
        .setContentIntent(intent)
        .setOnlyAlertOnce(true)
        .setOngoing(true)
        .setPriority(PRIORITY_MAX)

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

fun createNotificationCountdownViewAlive(context: Context): RemoteViews{
    val minuteSec = 60
    val hourSec = minuteSec * 60
    val daySec = hourSec * 24
    val totalSeconds = getSecondsUntilDate(Constants.EventDate.LUNAR_NEW_YEAR)
    val days = totalSeconds /  daySec
    val hours = (totalSeconds - days * daySec) / hourSec
    val minutes = (totalSeconds - days * daySec - hours * hourSec) / minuteSec
    val seconds = totalSeconds - days * daySec - hours * hourSec - minutes * minuteSec

    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.notification_view_count_down)
    views.setOnClickPendingIntent(R.id.layout_root,
        PendingIntent.getActivity(
            context,
            0,
            Intent(
                context, GetStartedScreen::class.java),
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
        1,
        Intent(context, UpdateCountDownServiceReceiver::class.java).apply {
            putExtra(RECEIVER_EVENT, ReceiverEvent.CLOSE_COUNT_DOWN)
        },
        PendingIntent.FLAG_IMMUTABLE
    ))

    return views
}

fun pushAlertNotification(context: Context){
    val notificationIntent = Intent(context, GetStartedScreen::class.java).apply {
        flags = (Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
    }
    val notificationManager: NotificationManager? = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

    val intent = PendingIntent.getActivity(
        context,
        2,
        notificationIntent,
        PendingIntent.FLAG_IMMUTABLE
    )

    val layoutAlert = if (isTetOnGoing()) R.layout.notification_layout_alert_on_tet else R.layout.notification_layout_alert

    val notificationLayout = RemoteViews(context.packageName, layoutAlert).apply {
        if (isTetOnGoing()){
            setTextViewText(R.id.tv_day_remain, ((getDaysUntilDate(Constants.EventDate.LUNAR_NEW_YEAR) * -1) + 1).toString())
        }
        else
        {
            setTextViewText(R.id.tv_day_remain, getDaysUntilDate(Constants.EventDate.LUNAR_NEW_YEAR).toString())
        }
        setOnClickPendingIntent(R.id.tv_follow, PendingIntent.getBroadcast(
            context,
            3,
            Intent(context, UpdateCountDownServiceReceiver::class.java).apply {
                putExtra(RECEIVER_EVENT,ReceiverEvent.PINNED_COUNT_DOWN)
            },
            PendingIntent.FLAG_IMMUTABLE
        ))
    }

    val notificationBuilder = NotificationCompat.Builder(context, "TET")
        .setSmallIcon(R.drawable.ic_notification)
        .setColor(AppResourcesProvider.getColor(R.color.colorPrimaryNotification))
        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
        .setCustomContentView(notificationLayout)
        .setContentIntent(intent)
        .setPriority(PRIORITY_MAX)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
        val channelId = "TET DEN ROI"
        val channel = NotificationChannel(
            channelId,
            "HPNY",
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager?.createNotificationChannel(channel)
        notificationBuilder.setChannelId(channelId)
    }

    notificationManager?.notify(FOREGROUND_ID, notificationBuilder.build())
}