package com.yeh35.android.stop_it.database.type

import androidx.room.TypeConverter
import org.joda.time.DateTime

class Converters {

    @TypeConverter
    fun toDate(dateString: String): DateTime {
        return DateTime.parse(dateString)
    }

    @TypeConverter
    fun toDateString(date: DateTime): String {
        return date.toString()
    }

}