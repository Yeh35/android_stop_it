package com.yeh35.android.stop_it.service

import android.R
import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.yeh35.android.stop_it.broadcast.AlarmReceiver
import com.yeh35.android.stop_it.broadcast.ScreenReceiver
import com.yeh35.android.stop_it.page.MainActivity
import java.util.*


class OnLockService : Service() {

    companion object {
        var serviceIntent: Intent? = null
    }

    private var mReceiver: BroadcastReceiver? = null

    override fun onCreate() {
        Log.d("OnLockService", "onCreate")
        super.onCreate()
        val filter = IntentFilter(Intent.ACTION_SCREEN_ON)
        filter.addAction(Intent.ACTION_SCREEN_OFF)
        mReceiver = ScreenReceiver()
        registerReceiver(mReceiver, filter)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("OnLockService", "onStartCommand")

        this.initializeNotification()

        if (intent == null) {
            val filter = IntentFilter(Intent.ACTION_SCREEN_ON)
            filter.addAction(Intent.ACTION_SCREEN_OFF)
            mReceiver = ScreenReceiver()
            registerReceiver(mReceiver, filter)

        } else {
            serviceIntent = intent
        }

        Log.d("OnLockService", "serviceIntent : " + serviceIntent.toString())
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.d("OnLockService", "onBind")
        return null
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun initializeNotification() {
        val builder = NotificationCompat.Builder(this, "1")
        builder.setSmallIcon(R.mipmap.sym_def_app_icon)
        val style = NotificationCompat.BigTextStyle()
        style.bigText("설정을 보려면 누르세요.")
        style.setBigContentTitle(null)
        style.setSummaryText("서비스 동작중")
        builder.setContentText(null)
        builder.setContentTitle(null)
        builder.setOngoing(true)
        builder.setStyle(style)
        builder.setWhen(0)
        builder.setShowWhen(false)

        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)
        builder.setContentIntent(pendingIntent)

        val manager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager.createNotificationChannel(
                NotificationChannel(
                    "1",
                    "undead_service",
                    NotificationManager.IMPORTANCE_NONE
                )
            )
        }

        val notification = builder.build()
        startForeground(1, notification)
    }

    override fun onDestroy() {
        super.onDestroy()

        if (mReceiver != null) {
            unregisterReceiver(mReceiver)
            mReceiver = null
        }


        Log.d("OnLockService", "onDestroy")

        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.add(Calendar.SECOND, 3)

        val intent = Intent(this, AlarmReceiver::class.java)
        val sender = PendingIntent.getBroadcast(this, 0, intent, 0)
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager[AlarmManager.RTC_WAKEUP, calendar.timeInMillis] = sender
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)

        if (mReceiver != null) {
            unregisterReceiver(mReceiver)
            mReceiver = null
        }

        Log.d("OnLockService", "onTaskRemoved")

        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.add(Calendar.SECOND, 3)

        val intent = Intent(this, AlarmReceiver::class.java)
        val sender = PendingIntent.getBroadcast(this, 0, intent, 0)
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager[AlarmManager.RTC_WAKEUP, calendar.timeInMillis] = sender
    }
}