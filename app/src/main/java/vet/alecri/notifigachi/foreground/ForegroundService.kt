package vet.alecri.notifigachi.foreground

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.support.v4.app.NotificationCompat
import android.util.Log
import android.widget.Toast
import android.app.NotificationManager
import android.app.NotificationChannel
import android.content.Context
import android.os.Build
import android.widget.RemoteViews
import vet.alecri.notifigachi.R

class MyForeGroundService : Service() {

    override fun onBind(intent: Intent): IBinder? {
        // TODO: Return the communication channel to the service.
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG_FOREGROUND_SERVICE, "My foreground service onCreate().")

        // As far as I can tell, onCreate() is only run once when the service is started

        // Create the notification channel so that we can create a notification
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Support for Android Oreo: Notification Channels
            val channel = NotificationChannel(
                "vet.alecri.notifigachi",
                "Notifigachi Persistent Notification",
                NotificationManager.IMPORTANCE_HIGH
            )
            manager.createNotificationChannel(channel)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        // As opposed to onCreate, onStartCommand is run each time an intent is sent to the service to represent an action

        if (intent != null) {
            val action = intent.action

            when (action) {
                ACTION_START_FOREGROUND_SERVICE -> {
                    startForegroundService()
                    Toast.makeText(applicationContext, "Foreground service is started.", Toast.LENGTH_LONG).show()
                }
                ACTION_STOP_FOREGROUND_SERVICE -> {
                    stopForegroundService()
                    Toast.makeText(applicationContext, "Foreground service is stopped.", Toast.LENGTH_LONG).show()
                }
                ACTION_PLAY -> Toast.makeText(applicationContext, "You click Play button.", Toast.LENGTH_LONG).show()
                ACTION_PAUSE -> Toast.makeText(applicationContext, "You click Pause button.", Toast.LENGTH_LONG).show()

                A_BUTTON -> Toast.makeText(applicationContext, "A Button", Toast.LENGTH_LONG).show()
                B_BUTTON -> Toast.makeText(applicationContext, "B Button", Toast.LENGTH_LONG).show()
                C_BUTTON -> Toast.makeText(applicationContext, "C Button", Toast.LENGTH_LONG).show()
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    /* Used to build and start foreground service. */
    fun startForegroundService() {
        Log.d(TAG_FOREGROUND_SERVICE, "Start foreground service.")

        // Create notification default intent.
        val intent = Intent()
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val notificationLayout = RemoteViews(packageName, vet.alecri.notifigachi.R.layout.custom_notification)

        // Set the remoteview Intents for our buttons
        setRemoteViews(notificationLayout)


        // Create notification builder.
        val builder = NotificationCompat.Builder(this,"vet.alecri.notifigachi" )
            .setSmallIcon(R.drawable.notification_icon_background)
//            .setStyle(NotificationCompat.DecoratedCustomViewStyle()) // dont call to ignore header
            .setCustomContentView(notificationLayout)
//            .setPriority(NotificationManager.IMPORTANCE_HIGH)
            .setFullScreenIntent(pendingIntent, true)


        // Build the notification.
        val notification = builder.build()

        // Start foreground service.
        startForeground(1, notification)


    }

    private fun setRemoteViews(remoteViews: RemoteViews) {

        // set Intent to open app on notification click.
        val openAppIntent = Intent(this, MyForeGroundService::class.java)

        // call broadcast when any control of notification is clicked.
        val aButtonIntent = Intent(MyForeGroundService.A_BUTTON)
        val bButtonIntent = Intent(MyForeGroundService.B_BUTTON)
        val cButtonIntent = Intent(MyForeGroundService.C_BUTTON)

        val pendingAButtonIntent = PendingIntent.getBroadcast(this, 0, aButtonIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val pendingBButtonIntent = PendingIntent.getBroadcast(this, 0, bButtonIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val pendingCButtonIntent = PendingIntent.getBroadcast(this, 0, cButtonIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        // Bind the onClickListener to the intended intents for each button
        remoteViews.setOnClickPendingIntent(R.id.AButton, pendingAButtonIntent)
        remoteViews.setOnClickPendingIntent(R.id.BButton, pendingBButtonIntent)
        remoteViews.setOnClickPendingIntent(R.id.CButton, pendingCButtonIntent)

    }

    fun stopForegroundService() {
        Log.d(TAG_FOREGROUND_SERVICE, "Stop foreground service.")

        // Stop foreground service and remove the notification.
        stopForeground(true)

        // Stop the foreground service.
        stopSelf()
    }


    companion object {

        private val TAG_FOREGROUND_SERVICE = "FOREGROUND_SERVICE"

        val ACTION_START_FOREGROUND_SERVICE = "ACTION_START_FOREGROUND_SERVICE"

        val ACTION_STOP_FOREGROUND_SERVICE = "ACTION_STOP_FOREGROUND_SERVICE"

        val ACTION_PAUSE = "ACTION_PAUSE"

        val ACTION_PLAY = "ACTION_PLAY"

        val A_BUTTON = "vet.alecri.notifigachi.AButton"

        val B_BUTTON = "vet.alecri.notifigachi.BButton"

        val C_BUTTON = "vet.alecri.notifigachi.CButton"

        val CLOSE_NOTIFICATION = "CLOSE_NOTIFICATION"
    }

}