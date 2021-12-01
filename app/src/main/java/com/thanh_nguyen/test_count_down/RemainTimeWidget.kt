package com.thanh_nguyen.test_count_down

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.thanh_nguyen.test_count_down.R
import com.thanh_nguyen.test_count_down.app.presentation.ui.SplashScreen
import com.thanh_nguyen.test_count_down.common.Constants
import com.thanh_nguyen.test_count_down.utils.cmn
import com.thanh_nguyen.test_count_down.utils.formatTwoNumber
import com.thanh_nguyen.test_count_down.utils.getSecondsUntilDate
import kotlinx.coroutines.*

/**
 * Implementation of App Widget functionality.
 */
internal var job: Job? = null

class RemainTimeWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        val appWidgetManager = AppWidgetManager.getInstance(
            context
        )
        val thisWidget = ComponentName(
            context?:return,
            RemainTimeWidget::class.java
        )
        val appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget)
        if (appWidgetIds != null && appWidgetIds.isNotEmpty()) {
            onUpdate(context, appWidgetManager, appWidgetIds)
        }
    }

    override fun onEnabled(context: Context) {
        cmn("isEnable")
        // Enter relevant functionality for when the first widget is created
        job = CoroutineScope(Dispatchers.IO).launch {
            while (true){
                delay(1000)
                context.sendBroadcast(Intent(context, RemainTimeWidget::class.java).apply {
                    action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
                })
            }
        }
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
        cmn("isDisable")
        job?.cancel()
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    val minuteSec = 60
    val hourSec = minuteSec * 60
    val daySec = hourSec * 24
    val totalSeconds = getSecondsUntilDate(Constants.EventDate.LUNAR_NEW_YEAR)
    val days = totalSeconds /  daySec
    val hours = (totalSeconds - days * daySec) / hourSec
    val minutes = (totalSeconds - days * daySec - hours * hourSec) / minuteSec
    val seconds = totalSeconds - days * daySec - hours * hourSec - minutes * minuteSec

    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.remain_time_widget)
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

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}