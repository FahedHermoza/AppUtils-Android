package com.fahed.developer.services_androidcore.broadcastReceiver

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class ResponseBroadcastReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        //get the broadcast message
        var resultCode: Int =intent.getIntExtra("resultCode",RESULT_CANCELED)
        if (resultCode == RESULT_OK){
            var message = intent.getStringExtra("toastMessage")
            Toast.makeText(context,message,Toast.LENGTH_LONG).show()
        }
    }
}