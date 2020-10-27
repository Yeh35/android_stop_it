package com.yeh35.android.stop_it.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.yeh35.android.stop_it.database.dao.PhoneUsageLogDao
import com.yeh35.android.stop_it.database.entity.PhoneUsageLog
import com.yeh35.android.stop_it.database.type.Converters

@Database(entities = [PhoneUsageLog::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun phoneUsageLogDao(): PhoneUsageLogDao

    companion object {

        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                AppDatabase::class.java, "app.db")
                .build()
    }
}