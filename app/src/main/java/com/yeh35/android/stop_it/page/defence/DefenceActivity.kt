package com.yeh35.android.stop_it.page.defence

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import com.bumptech.glide.Glide
import com.yeh35.android.stop_it.BuildConfig
import com.yeh35.android.stop_it.page.baase.BaseActivity
import com.yeh35.android.stop_it.util.preference.SharedPreferenceKey
import com.yeh35.android.stop_it.util.preference.SharedPreferenceManager
import com.yeh35.android.stop_it.R
import com.yeh35.android.stop_it.database.AppDatabase
import com.yeh35.android.stop_it.database.dao.DefenceUsageLogDao
import com.yeh35.android.stop_it.database.entity.DefenceUsageLog
import kotlinx.android.synthetic.main.activity_defance.*
import kotlinx.coroutines.launch
import org.joda.time.DateTime

class DefenceActivity : BaseActivity() {

    companion object {
        private var live  = false

        fun isLive(): Boolean {
            return live
        }
    }

    private lateinit var sharedPreferenceManager: SharedPreferenceManager
    private lateinit var defenceUsageLogDao: DefenceUsageLogDao
    private lateinit var phoneUsageLog: DefenceUsageLog

    private var repeatedInSecondsThreadStop = System.currentTimeMillis()
    private val endTime = DateTime.now().plusMinutes(10)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_defance)

        live = true

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        if (Build.VERSION.SDK_INT >= 27) {
            setShowWhenLocked(true)
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED)
        }

        Glide.with(this)
            .load(R.drawable.hourglass)
            .into(iv_hourglass)

        sharedPreferenceManager = SharedPreferenceManager(this)
        sharedPreferenceManager.set(SharedPreferenceKey.LAST_DEFENSE_RUNNING, DateTime.now())

        defenceUsageLogDao = AppDatabase.getInstance(this).defenceUsageLogDao()

        scopeIo.launch {
            phoneUsageLog = DefenceUsageLog()
            val id = defenceUsageLogDao.insert(phoneUsageLog)
            phoneUsageLog = defenceUsageLogDao.getFindId(id)
        }
    }

    override fun onStart() {
        super.onStart()

        if (phoneUsageLog.waited) {
            return
        }

        Thread(Runnable {
            val startingTime = System.currentTimeMillis()

            while (repeatedInSecondsThreadStop < startingTime) {
                val remaining = DateTime(endTime.millis - DateTime.now().millis)

                if (remaining.isAfter(1000)) {
                    Thread.sleep(1000)
                } else {
                    scopeMain.launch {
                        btn_pass.text = resources.getString(R.string.you_endured)
                    }

                    phoneUsageLog.waited()
                    defenceUsageLogDao.update(phoneUsageLog)
                    break
                }

                scopeMain.launch {
                    tv_time.text = resources.getString(
                        R.string.defence_time_format,
                        remaining.minuteOfHour,
                        remaining.secondOfMinute
                    )
                }
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
        repeatedInSecondsThreadStop = System.currentTimeMillis()
        live = false
    }

    fun onClickPass(v: View) {
        if (BuildConfig.DEBUG && v != btn_pass) {
            error("Assertion failed")
        }
        finish()
    }

    /**
     * Back 키 막기
     */
    override fun onBackPressed() {
        return
    }

    /**
     * Home 키 막기
     */
    override fun onUserLeaveHint() {
        finish()
    }

}