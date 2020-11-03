package com.yeh35.android.stop_it.service

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.IBinder
import android.os.SystemClock
import android.util.Log
import androidx.core.app.NotificationCompat
import com.yeh35.android.stop_it.R
import com.yeh35.android.stop_it.broadcast.RestartReceiver
import com.yeh35.android.stop_it.broadcast.ScreenReceiver
import com.yeh35.android.stop_it.page.MainActivity


class OnLockService : Service() {

    private var mReceiver: BroadcastReceiver? = null

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        Log.d("OnLockService", "onCreate")
        super.onCreate()

        val filter = IntentFilter(Intent.ACTION_SCREEN_ON)
        filter.addAction(Intent.ACTION_SCREEN_OFF)
        mReceiver = ScreenReceiver()
        registerReceiver(mReceiver, filter)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("OnLockService", "onStartCommand")

        //startForeground(1, Notification())

        /**
         * startForeground 를 사용하면 notification 을 보여주어야 하는데 없애기 위한 코드
         */
        val style = NotificationCompat.BigTextStyle()
        style.bigText("얼마나 모았을까요?")
        style.setBigContentTitle(null)
        style.setSummaryText("양말 수집중")

        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)

        val notification = NotificationCompat.Builder(this, "1")
            .setSmallIcon(R.mipmap.ic_dobby_round)
            .setContentText(null)
            .setContentTitle(null)
            .setOngoing(true)
            .setStyle(style)
            .setWhen(0)
            .setShowWhen(false)
            .setContentIntent(pendingIntent)
            .build()

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(
                NotificationChannel(
                    "1",
                    "잠금화면",
                    NotificationManager.IMPORTANCE_NONE
                )
            )
        }

        startForeground(1, notification)
//        notificationManager.notify(startId, notification)
//        notificationManager.cancel(startId)

        if (mReceiver == null) {
            val filter = IntentFilter(Intent.ACTION_SCREEN_ON)
            mReceiver = ScreenReceiver()
            registerReceiver(mReceiver, filter)
        }

        return START_STICKY
//        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()

        if (mReceiver != null) {
            unregisterReceiver(mReceiver)
            mReceiver = null
        }

        Log.d("OnLockService", "onDestroy")

        registerRestartAlarm()
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        Log.d("OnLockService", "onTaskRemoved")

        if (mReceiver != null) {
            unregisterReceiver(mReceiver)
            mReceiver = null
        }

        registerRestartAlarm()
    }

    /**
     * 알람 매니져에 서비스 등록
     */
    private fun registerRestartAlarm() {
        Log.i("OnLockService", "registerRestartAlarm")

        val intent = Intent(this, RestartReceiver::class.java)
        intent.action = RestartReceiver.ACTION_RESTART_ON_LOCK_SERVICE
        val sender = PendingIntent.getBroadcast(this, 0, intent, 0)

        val firstTime = SystemClock.elapsedRealtime() + 1*1000
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, firstTime, 1000, sender)
    }

    /**
     * 알람 매니져에 서비스 해제
     */
    private fun unregisterRestartAlarm() {
        Log.i("OnLockService", "unregisterRestartAlarm")

        val intent = Intent(this, RestartReceiver::class.java)
        intent.action = RestartReceiver.ACTION_RESTART_ON_LOCK_SERVICE
        val sender = PendingIntent.getBroadcast(this, 0, intent, 0)

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(sender)
    }

}