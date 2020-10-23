package com.yeh35.android.stop_it.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import com.yeh35.android.stop_it.BuildConfig

class ScreenReceiver : BroadcastReceiver() {

    companion object {

        fun registerReceiver(context: Context) {
            val intentFilter = IntentFilter(Intent.ACTION_SCREEN_ON)
            intentFilter.addAction(Intent.ACTION_SCREEN_OFF)
            val mReceiver: BroadcastReceiver = ScreenReceiver()
            context.registerReceiver(mReceiver, intentFilter)
        }
    }

    override fun onReceive(context: Context?, intent: Intent) {
        if (BuildConfig.DEBUG && !(intent.action == Intent.ACTION_SCREEN_OFF || intent.action == Intent.ACTION_SCREEN_ON)) {
            error("ACTION_SCREEN_OFF, ACTION_SCREEN_ON 만 허용됩니다.")
        }
    }

}