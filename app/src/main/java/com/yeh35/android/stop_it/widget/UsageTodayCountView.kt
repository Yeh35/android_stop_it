package com.yeh35.android.stop_it.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.getStringOrThrow
import com.bumptech.glide.load.engine.Resource
import com.yeh35.android.stop_it.R
import java.lang.ref.Reference
import java.text.NumberFormat
import java.util.*

class UsageTodayCountView(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {

    private val baseView: View
    private val tvTodayTime: TextView
    private val tvThanYesterdayTime: TextView
    private val imageUpDown: ImageView

    private var todayPlayCount: Int = 0
    private var yesterdayPlayCount: Int = 0

    private var countFormat: Int

    init {

        val li = getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        baseView = li.inflate(R.layout.view_usage_view, this, false)
        super.addView(baseView)

        tvTodayTime = baseView.findViewById(R.id.tv_today_time)
        tvThanYesterdayTime = baseView.findViewById(R.id.tv_than_yesterday_time)
        imageUpDown = baseView.findViewById(R.id.image_updown)

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.UsageTodayCountView,
            0, 0).apply {

            countFormat = getResourceId(R.styleable.UsageTodayCountView_count_format, R.string.int_format_count)
        }

        updateView()
    }

    fun setTodayPlayCount(count: Int) {
        todayPlayCount = count
        updateView()
    }

    fun setYesterdayPlayCount(count: Int) {
        yesterdayPlayCount = count
        updateView()
    }

    private fun updateView() {
        tvTodayTime.text = context.resources.getString(
            countFormat,
            NumberFormat.getNumberInstance(Locale.US).format(todayPlayCount)
        )

        val yesterdayCount = context.resources.getString(
            countFormat,
            NumberFormat.getNumberInstance(Locale.US).format(yesterdayPlayCount)
        )

        tvThanYesterdayTime.text = context.resources.getString(
            R.string.than_yesterday,
            yesterdayCount
        )

        if (todayPlayCount > yesterdayPlayCount) {
            imageUpDown.setImageResource(R.drawable.ic_red_arrow_circle_up_24)
        } else {
            imageUpDown.setImageResource(R.drawable.ic_blue_arrow_circle_down_24)
        }
    }
}