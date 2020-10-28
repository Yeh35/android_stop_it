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

    private val _todayUsageCount: MutableLiveData<Int> = MutableLiveData()
    val todayUsageCount: LiveData<Int>
        get() = _todayUsageCount

    private val _yesterdayUsageCount: MutableLiveData<Int> = MutableLiveData()
    val yesterdayUsageCount: LiveData<Int>
        get() = _yesterdayUsageCount

    private val _todaySocksCount: MutableLiveData<Int> = MutableLiveData()
    val todaySocksCount: LiveData<Int>
        get() = _todaySocksCount

    private val _yesterdaySocksCount: MutableLiveData<Int> = MutableLiveData()
    val yesterdaySocksCount: LiveData<Int>
        get() = _yesterdaySocksCount


    init {
        _todayUsageCount.value = 0
        _yesterdayUsageCount.value = 0

        _todaySocksCount.value = 0
        _yesterdaySocksCount.value = 0
    }

    fun refreshUsageCount() {
        scopeIo.launch {
            val fromToday= DateTime.now().withTime(0,0,0, 0)
            val toToday = fromToday.withTime(23,59,59, 59)
            val countToday = usageLogDao.getCountFindWakeUpDateTimeBetweenDates(fromToday, toToday)

            val fromYesterday= DateTime.now().minusDays(1).withTime(0,0,0, 0)
            val toYesterday= fromYesterday.withTime(23,59,59, 59)
            val countYesterday= usageLogDao.getCountFindWakeUpDateTimeBetweenDates(fromYesterday, toYesterday)

            scopeMain.launch {
                _todayUsageCount.value = countToday
                _yesterdayUsageCount.value = countYesterday
            }
        }

    }

    fun refreshSocksCount() {
        scopeIo.launch {
            val fromToday= DateTime.now().withTime(0,0,0, 0)
            val toToday = fromToday.withTime(23,59,59, 59)
            val countToday = usageLogDao.getSocksCountFindWakeUpDateTimeBetweenDates(fromToday, toToday)

            val fromYesterday= DateTime.now().minusDays(1).withTime(0,0,0, 0)
            val toYesterday= fromYesterday.withTime(23,59,59, 59)
            val countYesterday= usageLogDao.getSocksCountFindWakeUpDateTimeBetweenDates(fromYesterday, toYesterday)

            scopeMain.launch {
                _todaySocksCount.value = countToday
                _yesterdaySocksCount.value = countYesterday
            }
        }
    }


}