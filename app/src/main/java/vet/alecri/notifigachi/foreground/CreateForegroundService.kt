package vet.alecri.notifigachi.foreground

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import vet.alecri.notifigachi.R


import kotlinx.android.synthetic.main.custom_notification.*


class CreateForegroundServiceActivity : AppCompatActivity() { // implements View.OnClickListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_foreground_service)

        title = "Notifigachi"

        val startServiceButton = findViewById(R.id.start_foreground_service_button) as Button
        startServiceButton.setOnClickListener {
            val intent = Intent(this@CreateForegroundServiceActivity, MyForeGroundService::class.java)
            intent.action = MyForeGroundService.ACTION_START_FOREGROUND_SERVICE
            startService(intent)
        }

        val stopServiceButton = findViewById(R.id.stop_foreground_service_button) as Button
        stopServiceButton.setOnClickListener {
            val intent = Intent(this@CreateForegroundServiceActivity, MyForeGroundService::class.java)
            intent.action = MyForeGroundService.ACTION_STOP_FOREGROUND_SERVICE
            startService(intent)
        }
    }


}

class mCloseReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {

    }
}

class AButtonReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(context, "A Button", Toast.LENGTH_LONG).show()
        Log.d("BROADCAST", "A Button broadcast received")
    }
}

class BButtonReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(context, "B Button", Toast.LENGTH_LONG).show()
        Log.d("BROADCAST", "B Button broadcast received")

    }
}

class CButtonReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(context, "C Button", Toast.LENGTH_LONG).show()
        Log.d("BROADCAST", "C Button broadcast received")

    }
}