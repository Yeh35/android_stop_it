package com.yeh35.android.stop_it.page.home

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.siblingelement.location_alarm_android_app.ui.baase.BaseViewModel
import com.yeh35.android.stop_it.database.AppDatabase
import com.yeh35.android.stop_it.database.dao.DefenceUsageLogDao
import kotlinx.coroutines.launch
import org.joda.time.DateTime

class HomeViewModel(application: Application) : BaseViewModel(application) {

    private val usageLogDao: DefenceUsageLogDao = AppDatabase.getInstance(context).defenceUsageLogDao()

    private var _todayUsageCount: MutableLiveData<Int> = MutableLiveData()
    val todayUsageCount: LiveData<Int>
        get() = _todayUsageCount

    private var _yesterdayUsageCount: MutableLiveData<Int> = MutableLiveData()
    val yesterdayUsageCount: LiveData<Int>
        get() = _yesterdayUsageCount

    init {
        _todayUsageCount.value = 0
        _yesterdayUsageCount.value = 0
    }

    fun refreshTodayUsageCount() {
        scopeIo.launch {
            val from= DateTime.now().withTime(0,0,0, 0)
            val to= from.withTime(23,59,59, 59)
            val count = usageLogDao.getCountFindWakeUpDateTimeBetweenDates(from, to)

            scopeMain.launch {
                _todayUsageCount.value = count
            }
        }

    }

    fun refreshYesterdayUsageCount() {
        scopeIo.launch {
            val from= DateTime.now().minusDays(1).withTime(0,0,0, 0)
            val to= from.withTime(23,59,59, 59)
            val count= usageLogDao.getCountFindWakeUpDateTimeBetweenDates(from, to)

            scopeMain.launch {
                _yesterdayUsageCount.value = count
            }
        }


    }


}