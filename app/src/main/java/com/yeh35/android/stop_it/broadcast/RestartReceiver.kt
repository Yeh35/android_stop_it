package com.yeh35.android.stop_it.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.yeh35.android.stop_it.service.OnLockService

class RestartReceiver : BroadcastReceiver() {

    companion object {
        const val ACTION_RESTART_ON_LOCK_SERVICE = "com.yeh35.android.stop_it.action.restart.ON_LOCK_SERVICE"
    }

    override fun onReceive(context: Context, intent: Intent) {

        Log.i("RestartService" , "RestartService called : " + intent.action)

        when (intent.action) {
            ACTION_RESTART_ON_LOCK_SERVICE,
            Intent.ACTION_BOOT_COMPLETED -> {
                Log.i("RestartService" , "ON_LOCK_SERVICE")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val serviceIntent = Intent(context, OnLockService::class.java)
                    context.startForegroundService(serviceIntent)
                } else {
                    val serviceIntent = Intent(context, OnLockService::class.java)
                    context.startService(serviceIntent)
                }
            }

            else -> {
                error("정의하지 않은 ACTION 입니다.")
            }
        }
    }
}
