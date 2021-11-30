package com.thanh_nguyen.test_count_down.external.firebase

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.thanh_nguyen.test_count_down.App
import com.thanh_nguyen.test_count_down.BuildConfig

class AppAnalytics {
    companion object {
        private val firebaseAnalytic = FirebaseAnalytics.getInstance(App.getInstance())

        fun trackEventClickOpenWidget(){
            logEvent(Event.ClickOpenWidget)
        }

        fun trackEventCouldOpenWidget(){
            logEvent(Event.CouldOpenWidget)
        }

        private fun logEvent(event: Event){
            if (!BuildConfig.DEBUG){
                firebaseAnalytic.logEvent(event.action, event.params)
            }
        }
    }
}


sealed class Event(val action: String, val params: Bundle? = null){
    object ClickOpenWidget: Event(action = "click_open_widget")
    object CouldOpenWidget: Event(action = "can_open_widget")
}