package com.thanh_nguyen.test_count_down.service

import android.appwidget.AppWidgetManager
import android.content.Intent
import com.thanh_nguyen.test_count_down.RemainTimeWidget
import com.thanh_nguyen.test_count_down.utils.cmn
import kotlinx.coroutines.delay

class WidgetCountdownService: BaseService() {
    override fun onCreate() {
        super.onCreate()
        observeEvent {
            while (true){
                sendBroadcast(Intent(this, RemainTimeWidget::class.java).apply {
                    action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
                })
                delay(1000)
            }
        }
    }
}