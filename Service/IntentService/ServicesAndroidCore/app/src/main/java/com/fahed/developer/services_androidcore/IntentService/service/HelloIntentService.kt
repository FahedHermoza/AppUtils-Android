package com.fahed.developer.services_androidcore.IntentService.service

import android.app.Activity
import android.app.IntentService
import android.content.Intent
import android.util.Log

class HelloIntentService : IntentService("HelloIntentService") {
    var TAG = this.javaClass.name

    companion object{
        val ACTION = "com.fahed.developer.services_androidcore.IntentService.service.action"
    }

    override fun onHandleIntent(intent: Intent?) {
        Log.e(TAG, "IntentService running")
        try {
            Thread.sleep(5000)

            //create a broadcast to send the toast message
            var toastIntent = Intent(ACTION)
            toastIntent.putExtra("resultCode", Activity.RESULT_OK)
            toastIntent.putExtra("toastMessage","Estoy corriendo despues de haber ejecutado algun proceso en el IntentService")
            sendBroadcast(toastIntent)

            Log.e(TAG,"IntentService stop")
        } catch (e: InterruptedException) {
            // Restore interrupt status.
            Thread.currentThread().interrupt()
        }

    }


}