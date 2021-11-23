package com.thanh_nguyen.baseproject.external.firebase

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import com.thanh_nguyen.baseproject.App


class AppFirebaseMessageService: FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.e("new token", token)
    }

    override fun onMessageReceived(msg: RemoteMessage) {
        super.onMessageReceived(msg)
        Log.e("message receeived", "${Gson().toJson(msg)}")
    }

    companion object{

        fun init(context: Context){
            FirebaseApp.initializeApp(context)
        }

        fun getToken(){
            FirebaseMessaging.getInstance().token
                .addOnCompleteListener(object : OnCompleteListener<String?> {
                    override fun onComplete(task: Task<String?>) {
                        if (!task.isSuccessful) {
                            Log.e(
                                "TAG",
                                "Fetching FCM registration token failed",
                                task.exception
                            )
                            return
                        }

                        // Get new FCM registration token
                        val token: String = task.result ?:""

                        // Log and toast
                        Toast.makeText(App.getInstance(), token, Toast.LENGTH_SHORT).show()
                    }

                })
        }
    }
}