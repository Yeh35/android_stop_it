package com.yeh35.android.stop_it.service

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import android.util.Log
import com.yeh35.android.stop_it.broadcast.ScreenReceiver


class OnLockService : Service() {

    private var mReceiver: BroadcastReceiver? = null

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
        if (intent == null) {
            val filter = IntentFilter(Intent.ACTION_SCREEN_ON)
            filter.addAction(Intent.ACTION_SCREEN_OFF)
            mReceiver = ScreenReceiver()
            registerReceiver(mReceiver, filter)
        }
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.d("OnLockService", "onBind")
        return null
    }

    override fun onDestroy() {
        Log.d("OnLockService", "onDestroy")
        super.onDestroy()
        unregisterReceiver(mReceiver)
    }
}