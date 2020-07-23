package com.fahed.developer.services_androidcore.broadcastReceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.fahed.developer.services_androidcore.IntentService.service.HelloIntentService

class ToastBroadcastReceiver: BroadcastReceiver(){

    override fun onReceive(context: Context, intent: Intent) {
        //Start service
        val serviceIntent = Intent(context, HelloIntentService::class.java)
        context.startService(serviceIntent)
    }
}