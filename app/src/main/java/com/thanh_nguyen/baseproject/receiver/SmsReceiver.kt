package com.thanh_nguyen.baseproject.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.telephony.SmsMessage
import android.util.Log
import com.thanh_nguyen.baseproject.common.event.SingleLiveEvent

class SmsReceiver: BroadcastReceiver() {
    companion object {
        const val SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED"
        val onSmsReceived = SingleLiveEvent<Pair<String, String>>()
    }
    private val PDUS_TYPE = "pdus"
    override fun onReceive(context: Context?, intent: Intent?) {
        val pdus = intent?.extras?.get(PDUS_TYPE) as Array<*>?

        if (pdus == null || pdus.isEmpty())
            return

        val messages = arrayOfNulls<SmsMessage>(pdus.size)
        val sb = StringBuilder()
        for (i in pdus.indices) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                val format = intent?.extras?.getString("format")
                messages[i] = SmsMessage.createFromPdu(pdus[i] as ByteArray?, format)
            }
            else
                messages[i] = SmsMessage.createFromPdu(pdus[i] as ByteArray?)

            sb.append(messages[i]?.messageBody)
        }
        val sender = messages[0]?.originatingAddress?:""
        val message = sb.toString()
        executeMessage(sender, message)
    }

    private fun executeMessage(sender: String, msg: String) {
        Log.e("received", "from: $sender, msg = $msg")
        onSmsReceived.postValue(Pair(sender, msg))
    }
}