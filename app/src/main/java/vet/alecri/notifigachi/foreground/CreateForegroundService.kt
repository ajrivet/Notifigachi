package vet.alecri.notifigachi.foreground

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.Toast
import vet.alecri.notifigachi.R

class CreateForegroundServiceActivity : AppCompatActivity() {

    private val br = ButtonBroadcastReceiver()

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

        // Setup a broadcast filter for our button presses
        val filter = IntentFilter()
        filter.addAction(MyForeGroundService.A_BUTTON)
        filter.addAction(MyForeGroundService.B_BUTTON)
        filter.addAction(MyForeGroundService.C_BUTTON)


        // Register the Receiver for this context
        this.registerReceiver(br, filter)

    }

    override fun onDestroy() {

        // Unregister the broadcast receiver
        this.unregisterReceiver(br)

        super.onDestroy()
    }

}

// This is where we handle button presses haha
class ButtonBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("BROADCASTRECEIVER", "Receiver a broadcast" + intent.action)

        if (intent != null) {
            val action = intent.action

            when (action) {
                MyForeGroundService.A_BUTTON -> Toast.makeText(context, "A Button", Toast.LENGTH_LONG).show()
                MyForeGroundService.B_BUTTON -> Toast.makeText(context, "B Button", Toast.LENGTH_LONG).show()
                MyForeGroundService.C_BUTTON -> Toast.makeText(context, "C Button", Toast.LENGTH_LONG).show()
                else -> Toast.makeText(context, "BROADCAST NOT RECOGNIZED", Toast.LENGTH_LONG).show()
            }
        } else {
            Log.d("ButtonBroadcastReceiver", "Received null broadcast message")
        }
    }
}
