package com.yeh35.android.stop_it.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class PowerSendReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent) {

        when {
            intent.action.equals(Intent.ACTION_SCREEN_OFF) -> {
                Log.d("In on receive", "In Method:  ACTION_SCREEN_OFF")
            }
            intent.action.equals(Intent.ACTION_SCREEN_ON) -> {
                Log.d("In on receive", "In Method:  ACTION_SCREEN_ON")
            }
            intent.action.equals(Intent.ACTION_USER_PRESENT) -> {
                Log.e("In on receive", "In Method:  ACTION_USER_PRESENT")
            }
        }
    }

}