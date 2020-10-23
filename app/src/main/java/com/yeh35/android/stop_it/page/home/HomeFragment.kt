package com.yeh35.android.stop_it.page.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.siblingelement.location_alarm_android_app.ui.baase.BaseFragment
import com.yeh35.android.stop_it.R
import com.yeh35.android.stop_it.widget.UsageTodayView
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment() {

    private lateinit var usageTodayView: UsageTodayView

    private var repeatedInSecondsThreadStop = System.currentTimeMillis()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        baseView = inflater.inflate(R.layout.fragment_home, container, false)
        usageTodayView = baseView.findViewById(R.id.usage_today)
        return baseView
    }

    override fun onStart() {
        super.onStart()

        Thread(Runnable {
            val startingTime = System.currentTimeMillis()

            while (repeatedInSecondsThreadStop < startingTime) {
                scopeMain.launch {
                    usageTodayView.tick()
                }
                Thread.sleep(1000)
            }

            Log.d("repeatedInSecondsThread", "stop")
        }).start()
    }

    override fun onStop() {
        super.onStop()
        repeatedInSecondsThreadStop = System.currentTimeMillis()
    }
}