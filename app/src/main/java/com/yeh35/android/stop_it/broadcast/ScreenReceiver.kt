package com.yeh35.android.stop_it.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.yeh35.android.stop_it.util.preference.SharedPreferenceKey
import com.yeh35.android.stop_it.util.preference.SharedPreferenceManager
import com.yeh35.android.stop_it.BuildConfig
import com.yeh35.android.stop_it.page.defence.DefenceActivity
import org.joda.time.DateTime

class ScreenReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent) {
        if (BuildConfig.DEBUG && !(intent.action == Intent.ACTION_SCREEN_OFF || intent.action == Intent.ACTION_SCREEN_ON)) {
            error("ACTION_SCREEN_OFF, ACTION_SCREEN_ON 만 허용됩니다.")
        }

        Log.d("ScreenReceiver", "on ScreenReceiver")

        val sharedPreferenceManager = SharedPreferenceManager(context!!)

        if (intent.action == Intent.ACTION_SCREEN_ON) {
            val lastDefenseRunning = sharedPreferenceManager.get(SharedPreferenceKey.LAST_DEFENSE_RUNNING) as DateTime
//            val isDefenseRunning = sharedPreferenceManager.get(SharedPreferenceKey.IS_DEFENSE_RUNNING) as Boolean

            if (lastDefenseRunning.plusMinutes(30).isBeforeNow || !DefenceActivity.isLive()) {
                val startIntent = Intent(context, DefenceActivity::class.java)
                startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(startIntent)
            }
        }
    }
}