package com.yeh35.android.stop_it.widget

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.res.getStringOrThrow
import com.yeh35.android.stop_it.R

open class TitleFrameLayout(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {

    private val baseView: View
    private val frame: FrameLayout
    private val tvTitle: TextView

    /**
     * Attributes
     */
    private val title: String

    init {
        val li = getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        baseView = li.inflate(R.layout.view_title_framelayout, this, false)
        super.addView(baseView)

        frame = baseView.findViewById(R.id.frame)
        tvTitle = baseView.findViewById(R.id.tv_title)

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.TitleFrameLayout,
            0, 0).apply {

            title = getStringOrThrow(R.styleable.TitleFrameLayout_title)
            tvTitle.text = title
        }
    }

    override fun addView(child: View?, index: Int) {
        if (baseView == child) {
            super.addView(child, index)
        } else {
            frame.addView(child, index)
            invalidate()
        }
    }

    override fun addView(child: View?, params: ViewGroup.LayoutParams?) {
        if (baseView == child) {
            super.addView(child, params)
        } else {
            frame.addView(child, params)
            invalidate()
        }
    }

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (baseView == child) {
            super.addView(child, index, params)
        } else {
            frame.addView(child, index, params)
            invalidate()
        }
    }

    override fun addView(child: View?, width: Int, height: Int) {
        if (baseView == child) {
            super.addView(child, width, height)
        } else {
            frame.addView(child, width, height)
            invalidate()
        }
    }

    override fun addView(child: View?) {
        if (baseView == child) {
            super.addView(child)
        } else {
            frame.addView(child)
            invalidate()
        }
    }

}