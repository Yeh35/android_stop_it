package com.yeh35.android.stop_it.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.yeh35.android.stop_it.R

class UsageTodayView(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {

    companion object {
        private const val DAY_IN_SECONDS = 24 * 60 * 60
        private const val HOUR_IN_SECONDS = 60 * 60
        private const val MINUTE_IN_SECONDS = 60
    }

    private val baseView: View
    private val tvTodayTime: TextView
    private val tvThanYesterdayTime: TextView
    private val imageUpDown: ImageView

    private var todayPlayTime: Int = 0
    private var yesterdayPlayTime: Int = 0

    init {

        val li = getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        baseView = li.inflate(R.layout.view_usage_view, this, false)
        super.addView(baseView)

        tvTodayTime = baseView.findViewById(R.id.tv_today_time)
        tvThanYesterdayTime = baseView.findViewById(R.id.tv_than_yesterday_time)
        imageUpDown = baseView.findViewById(R.id.image_updown)

        updateView()
    }

    fun tick() {
        addTodayPlayTime(1)
    }

    fun getYesterdayPlayTime(): Int {
        return yesterdayPlayTime
    }

    fun getTodayPlayTime(): Int {
        return todayPlayTime
    }

    fun addYesterdayPlayTime(hour: Int, minute: Int, second: Int) {
        yesterdayPlayTime += (hour * HOUR_IN_SECONDS + minute * MINUTE_IN_SECONDS + second)
        yesterdayPlayTime %= DAY_IN_SECONDS
        updateView()
    }

    fun addYesterdayPlayTime(minute: Int, second: Int) {
        addYesterdayPlayTime(0, minute, second)
    }

    fun addYesterdayPlayTime(second: Int) {
        addYesterdayPlayTime(0, 0, second)
    }

    fun addTodayPlayTime(hour: Int, minute: Int, second: Int) {
        todayPlayTime += (hour * HOUR_IN_SECONDS + minute * MINUTE_IN_SECONDS + second)
        todayPlayTime %= DAY_IN_SECONDS
        updateView()
    }

    fun addTodayPlayTime(minute: Int, second: Int) {
        addTodayPlayTime(0, minute, second)
    }

    fun addTodayPlayTime(second: Int) {
        addTodayPlayTime(0, 0, second)
    }

    private fun updateView() {
        var playTime = todayPlayTime;
        val todaySeconds: Int = playTime % MINUTE_IN_SECONDS
        playTime -= todaySeconds
        val todayMinute: Int = playTime % HOUR_IN_SECONDS / MINUTE_IN_SECONDS
        playTime -= todayMinute
        val todayHour: Int = todayPlayTime / HOUR_IN_SECONDS

        tvTodayTime.text = String.format("%02d : %02d : %02d", todayHour, todayMinute, todaySeconds)

        var gapTime = todayPlayTime - yesterdayPlayTime
        val gapSeconds: Int = gapTime % MINUTE_IN_SECONDS
        gapTime -= gapSeconds
        val gapMinute: Int = gapTime % HOUR_IN_SECONDS / MINUTE_IN_SECONDS
        gapTime -= gapMinute
        val gapHour: Int = gapTime / HOUR_IN_SECONDS

        var thanYesterdayText = context.resources.getString(R.string.than_yesterday)
        thanYesterdayText += if (gapHour > 0) "${gapHour}시간" else ""
        thanYesterdayText += "${gapMinute}분"
        tvThanYesterdayTime.text = thanYesterdayText

        if (gapTime > 0) {
            imageUpDown.setImageResource(R.drawable.ic_red_arrow_circle_up_24)
        } else {
            imageUpDown.setImageResource(R.drawable.ic_blue_arrow_circle_down_24)
        }
    }
}