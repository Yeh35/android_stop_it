package com.yeh35.android.stop_it.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_BOOT_COMPLETED
import android.os.Build
import android.util.Log
import com.yeh35.android.stop_it.service.OnLockService
import com.yeh35.android.stop_it.util.ServiceUtil

class RestartReceiver : BroadcastReceiver() {

    companion object {
        const val ACTION_RESTART_ON_LOCK_SERVICE = "com.yeh35.android.stop_it.action.restart.ON_LOCK_SERVICE"
    }

    override fun onReceive(context: Context, intent: Intent) {

        Log.i("RestartService" , "RestartService called : " + intent.action)

        when (intent.action) {
            ACTION_RESTART_ON_LOCK_SERVICE,
            ACTION_BOOT_COMPLETED -> {
                Log.i("RestartService" , "ON_LOCK_SERVICE")
                if (!ServiceUtil.isServiceRunning(context, OnLockService.javaClass)) {
                    val serviceIntent = Intent(context, OnLockService::class.java)

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        context.startForegroundService(serviceIntent)
                    } else {
                        context.startService(serviceIntent)
                    }
                }
            }

            else -> {
                error("정의하지 않은 ACTION 입니다.")
            }
        }
    }
}
