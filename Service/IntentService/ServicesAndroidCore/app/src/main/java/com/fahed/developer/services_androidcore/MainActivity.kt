package com.fahed.developer.services_androidcore

import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.fahed.developer.services_androidcore.broadcastReceiver.ResponseBroadcastReceiver
import com.fahed.developer.services_androidcore.broadcastReceiver.ToastBroadcastReceiver
import com.fahed.developer.services_androidcore.IntentService.service.HelloIntentService

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
/***
 * Servicio vs IntentService en la plataforma Android
 * https://stackoverflow.com/questions/15524280/service-vs-intentservice-in-the-android-platform/15772151#15772151
 *
 * Example
 * https://medium.com/@harunwangereka/android-background-services-b5aac6be3f04
 * https://github.com/wangerekaharun/AndroidBackgroundSevices
 * ***/
class MainActivity : AppCompatActivity() {
    lateinit var broadcastReceiver: ResponseBroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        broadcastReceiver = ResponseBroadcastReceiver()

        val intentFilter =  IntentFilter()
        intentFilter.addAction(HelloIntentService.ACTION)
        registerReceiver(broadcastReceiver,intentFilter)

        buttonIntentService.setOnClickListener {
            ToastBroadcastReceiver()
                .onReceive(baseContext, intent)
        }

    }

    override fun onResume() {
        super.onResume()
        var intentFilter = IntentFilter()
        intentFilter.addAction(HelloIntentService.ACTION)
        registerReceiver(broadcastReceiver,intentFilter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(broadcastReceiver)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
