package com.yeh35.android.stop_it.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.yeh35.android.stop_it.BuildConfig

class BootingReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (BuildConfig.DEBUG && intent!!.action != Intent.ACTION_BOOT_COMPLETED) {
            error("ACTION_BOOT_COMPLETED만으로 실행되어야 합니다.")
        }

        ScreenReceiver.registerReceiver(context!!)
    }
}