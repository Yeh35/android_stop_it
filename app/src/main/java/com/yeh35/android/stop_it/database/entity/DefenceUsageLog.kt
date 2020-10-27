package com.yeh35.android.stop_it.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.yeh35.android.stop_it.BuildConfig
import org.joda.time.DateTime

@Entity(
    tableName = "defence_usage_log"
)
class DefenceUsageLog : BaseEntity() {

    @ColumnInfo(name = "wake_up_date_time")
    var wakeUpDateTime: DateTime = DateTime.now()

    @ColumnInfo(name = "waited")
    var waited: Boolean

    init {
        this.waited = false
    }

    fun waited() {
        waited = true
    }

}