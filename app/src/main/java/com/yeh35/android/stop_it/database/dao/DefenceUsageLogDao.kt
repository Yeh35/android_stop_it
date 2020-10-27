package com.yeh35.android.stop_it.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.yeh35.android.stop_it.database.entity.DefenceUsageLog

@Dao
abstract class DefenceUsageLogDao : AbstractBaseDao<DefenceUsageLog>() {

    @Query("SELECT * FROM defence_usage_log")
    abstract fun getAll(): List<DefenceUsageLog>

    @Query("SELECT * FROM defence_usage_log WHERE ID = :id")
    abstract fun getFindId(id: Long): DefenceUsageLog

}