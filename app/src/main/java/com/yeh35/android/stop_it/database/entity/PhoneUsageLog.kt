package com.yeh35.android.stop_it.database.entity

import androidx.room.Entity
import org.joda.time.DateTime

@Entity(
    tableName = "phone_usage_log"
)
class PhoneUsageLog(private var wakeUpDateTime: DateTime) : BaseEntity() {

    private var sleepDateTime: DateTime? = null

    fun setSleepDateTime(dateTime: DateTime) {
        sleepDateTime = dateTime
    }

    fun getUseTime(): DateTime? {
        return if (sleepDateTime == null) {
            null
        } else {
            sleepDateTime!!.minus(wakeUpDateTime.millis)
        }

    }
}