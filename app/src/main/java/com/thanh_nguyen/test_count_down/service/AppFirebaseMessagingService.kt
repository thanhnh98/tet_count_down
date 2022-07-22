package com.thanh_nguyen.test_count_down.service

import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.thanh_nguyen.test_count_down.utils.WTF

class AppFirebaseMessagingService: FirebaseMessagingService() {

    override fun onCreate() {
        super.onCreate()
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }
            val token = task.result

            // Log and toast
            WTF(token)
        })
    }

    override fun onNewToken(p0: String) {
        WTF("TOKEN: ${p0}")
        super.onNewToken(p0)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        WTF("${remoteMessage.notification?.body}")
//        Toast.makeText(this, "VCL",Toast.LENGTH_SHORT).show()
    }
}