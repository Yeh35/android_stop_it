package com.yeh35.android.stop_it.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.yeh35.android.stop_it.database.entity.DefenceUsageLog
import org.joda.time.DateTime

@Dao
abstract class DefenceUsageLogDao : AbstractBaseDao<DefenceUsageLog>() {

    @Query("SELECT * FROM defence_usage_log")
    abstract fun getAll(): List<DefenceUsageLog>

    @Query("SELECT * FROM defence_usage_log WHERE ID = :id")
    abstract fun getFindId(id: Long): DefenceUsageLog

    @Query("SELECT * FROM defence_usage_log WHERE wake_up_date_time BETWEEN :from AND :to")
    abstract fun getFindWakeUpDateTimeBetweenDates(from: DateTime, to: DateTime): List<DefenceUsageLog>

    @Query("SELECT COUNT(*) FROM defence_usage_log WHERE wake_up_date_time BETWEEN :from AND :to")
    abstract fun getCountFindWakeUpDateTimeBetweenDates(from: DateTime, to: DateTime): Int
}