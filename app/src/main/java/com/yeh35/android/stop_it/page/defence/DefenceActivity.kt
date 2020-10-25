package com.yeh35.android.stop_it.page.defence

import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.siblingelement.location_alarm_android_app.ui.baase.BaseActivity
import com.siblingelement.location_alarm_android_app.util.preference.SharedPreferenceKey
import com.yeh35.android.stop_it.util.preference.SharedPreferenceManager
import com.yeh35.android.stop_it.R
import kotlinx.android.synthetic.main.activity_defance.*
import kotlinx.coroutines.launch
import org.joda.time.DateTime

class DefenceActivity : BaseActivity() {

    private lateinit var sharedPreferenceManager: SharedPreferenceManager
    private var repeatedInSecondsThreadStop = System.currentTimeMillis()
    private val endTime = DateTime.now().plusMinutes(10)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_defance)

        Glide.with(this)
            .load(R.drawable.hourglass)
            .into(iv_hourglass)

        sharedPreferenceManager = SharedPreferenceManager(this)
        sharedPreferenceManager.set(SharedPreferenceKey.IS_DEFENSE_RUNNING, true)
    }

    override fun onStart() {
        super.onStart()

        Thread(Runnable {
            val startingTime = System.currentTimeMillis()

            while (repeatedInSecondsThreadStop < startingTime) {
                scopeMain.launch {

                    val remaining = DateTime(endTime.millis - DateTime.now().millis)
                    tv_time.text = "${remaining.minuteOfHour} : ${remaining.secondOfMinute}"
                }
                Thread.sleep(1000)
            }

            Log.d("repeatedInSecondsThread", "stop")
        }).start()
    }

    override fun onStop() {
        repeatedInSecondsThreadStop = System.currentTimeMillis()
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        sharedPreferenceManager.set(SharedPreferenceKey.IS_DEFENSE_RUNNING, false)
    }

    fun onClickPass(v: View) {
        finish()
    }

    override fun onBackPressed() {
        return
    }

}