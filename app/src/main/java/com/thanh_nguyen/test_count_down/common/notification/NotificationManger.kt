package com.thanh_nguyen.test_count_down.common.notification

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
import com.thanh_nguyen.test_count_down.BuildConfig
import com.thanh_nguyen.test_count_down.R
import com.thanh_nguyen.test_count_down.app.presentation.ui.SplashScreen
import com.thanh_nguyen.test_count_down.common.Constants
import com.thanh_nguyen.test_count_down.utils.getDaysUntilDate
import com.thanh_nguyen.test_count_down.utils.getSecondsUntilDate


@RequiresApi(Build.VERSION_CODES.O)
fun pushNotification(context: Context){
    val notificationIntent = Intent(context, SplashScreen::class.java)
    val notificationManager: NotificationManager? = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

    notificationIntent.flags = (Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)

    val intent = PendingIntent.getActivity(
        context,
        123,
        notificationIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )
//
//    val notificationLayout = RemoteViews("com.thanh_nguyen.test_count_down", R.layout.notification_small)
//    val notificationLayoutExpanded = RemoteViews(packageName, R.layout.notification_large)
//
    val notificationBuilder = NotificationCompat.Builder(context, "TET")
        .setSmallIcon(R.mipmap.ic_launcher)
        .setContentTitle("SẮP ĐẾN TẾT ROOIFIII !!!")
        .setContentText("Chỉ còn ${getDaysUntilDate(Constants.EventDate.LUNAR_NEW_YEAR)} ngày nữa là tết rồi <3")
        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
        .setContentIntent(intent)

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

    notificationManager?.notify(123456, notificationBuilder.build())
}