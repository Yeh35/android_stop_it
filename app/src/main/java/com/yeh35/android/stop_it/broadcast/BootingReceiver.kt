package com.yeh35.android.stop_it.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.yeh35.android.stop_it.BuildConfig
import com.yeh35.android.stop_it.service.OnLockService

class BootingReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (BuildConfig.DEBUG && intent!!.action != Intent.ACTION_BOOT_COMPLETED) {
            error("'ACTION_BOOT_COMPLETED'만으로 실행되어야 합니다.")
        }

        val serviceIntent = Intent(context, OnLockService::class.java)
        context!!.startService(serviceIntent)
    }
}