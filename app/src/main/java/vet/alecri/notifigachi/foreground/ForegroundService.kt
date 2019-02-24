package vet.alecri.notifigachi.foreground

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
                "Channel_name_to_be_displayed_in_Settings",
                NotificationManager.IMPORTANCE_DEFAULT
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
    private fun startForegroundService() {
        Log.d(TAG_FOREGROUND_SERVICE, "Start foreground service.")

        // Create notification default intent.
        val intent = Intent()
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val notificationLayout = RemoteViews(packageName, R.layout.custom_notification)

        // Create notification builder.
        val builder = NotificationCompat.Builder(this,"vet.alecri.notifigachi" )
            .setSmallIcon(R.drawable.notification_icon_background)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setCustomContentView(notificationLayout)
            .setCustomBigContentView(notificationLayout)


        // Make notification show big text.
        val bigTextStyle = NotificationCompat.BigTextStyle()
        bigTextStyle.setBigContentTitle("Music player implemented by foreground service.")
        bigTextStyle.bigText("Android foreground service is a android service which can run in foreground always, it can be controlled by user via notification.")
        // Set big text style.
        builder.setStyle(bigTextStyle)

        builder.setWhen(System.currentTimeMillis())
        builder.setSmallIcon(vet.alecri.notifigachi.R.mipmap.ic_launcher)
        val largeIconBitmap = BitmapFactory.decodeResource(resources, vet.alecri.notifigachi.R.drawable.ic_fiber_manual_record_black_24dp)
        builder.setLargeIcon(largeIconBitmap)

        // Make the notification max priority.
        builder.priority = Notification.PRIORITY_MAX

        // Make head-up notification.
        builder.setFullScreenIntent(pendingIntent, true)

        // Add Play button intent in notification.
        val playIntent = Intent(this, MyForeGroundService::class.java)
        playIntent.action = ACTION_PLAY
        val pendingPlayIntent = PendingIntent.getService(this, 0, playIntent, 0)
        val playAction = NotificationCompat.Action(android.R.drawable.ic_media_play, "Play", pendingPlayIntent)
        builder.addAction(playAction)

        // Add Pause button intent in notification.
        val pauseIntent = Intent(this, MyForeGroundService::class.java)
        pauseIntent.action = ACTION_PAUSE
        val pendingPrevIntent = PendingIntent.getService(this, 0, pauseIntent, 0)
        val prevAction = NotificationCompat.Action(android.R.drawable.ic_media_pause, "Pause", pendingPrevIntent)
        builder.addAction(prevAction)

        // Build the notification.
        val notification = builder.build()

        // Start foreground service.
        startForeground(1, notification)
    }

    private fun stopForegroundService() {
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

        val A_BUTTON = "A_BUTTON"

        val B_BUTTON = "B_BUTTON"

        val C_BUTTON = "C_BUTTON"
    }
}