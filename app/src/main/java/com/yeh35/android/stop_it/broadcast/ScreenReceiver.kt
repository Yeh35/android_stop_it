package com.yeh35.android.stop_it.broadcast

import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.siblingelement.location_alarm_android_app.util.preference.SharedPreferenceKey
import com.yeh35.android.stop_it.util.preference.SharedPreferenceManager
import com.yeh35.android.stop_it.BuildConfig
import com.yeh35.android.stop_it.R
import com.yeh35.android.stop_it.page.defence.DefenceActivity

class ScreenReceiver : BroadcastReceiver() {



    override fun onReceive(context: Context?, intent: Intent) {
        if (BuildConfig.DEBUG && !(intent.action == Intent.ACTION_SCREEN_OFF || intent.action == Intent.ACTION_SCREEN_ON)) {
            error("ACTION_SCREEN_OFF, ACTION_SCREEN_ON 만 허용됩니다.")
        }

        val sharedPreferenceManager = SharedPreferenceManager(context!!)

        if (intent.action == Intent.ACTION_SCREEN_ON) {
            val isDefenseRunning = sharedPreferenceManager.get(SharedPreferenceKey.IS_DEFENSE_RUNNING) as Boolean
//            val isMainRunning = sharedPreferenceManager.get(SharedPreferenceKey.IS_MAIN_RUNNING) as Boolean

            if (!isDefenseRunning) {
                val startIntent = Intent(context, DefenceActivity::class.java)
                startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(startIntent)
            }
        }
    }

    companion object {

        fun registerReceiver(context: Context) {
            val intentFilter = IntentFilter(Intent.ACTION_SCREEN_ON)
            intentFilter.addAction(Intent.ACTION_SCREEN_OFF)
            val mReceiver: BroadcastReceiver = ScreenReceiver()
            context.registerReceiver(mReceiver, intentFilter)
        }
    }
}