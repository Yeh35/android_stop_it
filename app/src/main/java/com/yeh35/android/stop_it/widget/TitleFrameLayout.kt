package com.yeh35.android.stop_it.widget

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
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

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        tvTitle.text = title
    }

    override fun addView(child: View?) {
        frame.addView(child)
    }
}