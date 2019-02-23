package vet.alecri.notifigachi.foreground

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import vet.alecri.notifigachi.R


class CreateForegroundServiceActivity : AppCompatActivity() { // implements View.OnClickListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_foreground_service)

        title = "dev2qa.com - Android Foreground Service Example."

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