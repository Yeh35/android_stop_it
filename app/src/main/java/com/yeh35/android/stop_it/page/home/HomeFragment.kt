package com.yeh35.android.stop_it.page.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.siblingelement.location_alarm_android_app.ui.baase.BaseFragment
import com.yeh35.android.stop_it.R
import com.yeh35.android.stop_it.widget.UsageTodayCountView

class HomeFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var viewModel: HomeViewModel

    private lateinit var socksCountView: UsageTodayCountView
    private lateinit var usageCountView: UsageTodayCountView
    private lateinit var swipeRefresh: SwipeRefreshLayout
//    private lateinit var barChart: BarChart

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        baseView = inflater.inflate(R.layout.fragment_home, container, false)

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        swipeRefresh = baseView.findViewById(R.id.swipe_layout)
        swipeRefresh.setOnRefreshListener(this)

        socksCountView = baseView.findViewById(R.id.socks_count)
        viewModel.todaySocksCount.observe(this.viewLifecycleOwner, Observer {count ->
            socksCountView.setTodayPlayCount(count)
        })
        viewModel.yesterdaySocksCount.observe(this.viewLifecycleOwner, Observer {count ->
            socksCountView.setYesterdayPlayCount(count)
        })

        usageCountView = baseView.findViewById(R.id.usage_count)
        viewModel.todayUsageCount.observe(this.viewLifecycleOwner, Observer {count ->
            usageCountView.setTodayPlayCount(count)
        })
        viewModel.yesterdayUsageCount.observe(this.viewLifecycleOwner, Observer {count ->
            usageCountView.setYesterdayPlayCount(count)
        })

//        barChart = baseView.findViewById(R.id.barChart)

        refresh()
        return baseView
    }

    override fun onRefresh() {
        refresh()
        swipeRefresh.isRefreshing = false
    }

    private fun refresh() {
        viewModel.refreshUsageCount()
        viewModel.refreshSocksCount()
    }

    //TODO 차트를 하려고 했지만 귀찮음으로.. pass
//    private fun setChar() {
//        barChart.xAxis.apply {
//            position = XAxis.XAxisPosition.TOP // X축 데이터의 위치
//            textSize = 10f //텍스트 크기 지정
//            setDrawGridLines(false) // 배경 그리드 라인 세팅
//            granularity = 1f // x 축 데이터 표시 간격
//            axisMinimum = 2f // x 축 데이터 최소 표시값
//            isGranularityEnabled = false // x 축 간격을 제한하는 세분화 기능
//        }
//
//        barChart.apply {
//            axisRight.isEnabled = false // y축
//        }
//    }
}