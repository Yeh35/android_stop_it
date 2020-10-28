package com.yeh35.android.stop_it.page.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.siblingelement.location_alarm_android_app.ui.baase.BaseFragment
import com.yeh35.android.stop_it.R
import com.yeh35.android.stop_it.widget.UsageTodayCountView
import com.yeh35.android.stop_it.widget.UsageTodayView
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var viewModel: HomeViewModel

    private lateinit var usageTodayCountView: UsageTodayCountView
    private lateinit var swipeRefresh: SwipeRefreshLayout


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        baseView = inflater.inflate(R.layout.fragment_home, container, false)

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        usageTodayCountView = baseView.findViewById(R.id.usage_today_count)
        swipeRefresh = baseView.findViewById(R.id.swipe_layout)
        swipeRefresh.setOnRefreshListener(this)

        viewModel.todayUsageCount.observe(this.viewLifecycleOwner, Observer {count ->
            usageTodayCountView.setTodayPlayCount(count)
        })

        viewModel.yesterdayUsageCount.observe(this.viewLifecycleOwner, Observer {count ->
            usageTodayCountView.setYesterdayPlayCount(count)
        })

        refresh()
        return baseView
    }

    override fun onRefresh() {
        refresh()
        swipeRefresh.isRefreshing = false
    }

    private fun refresh() {
        viewModel.refreshTodayUsageCount()
        viewModel.refreshYesterdayUsageCount()
    }
}