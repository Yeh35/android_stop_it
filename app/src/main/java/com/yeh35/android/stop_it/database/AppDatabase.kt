package com.yeh35.android.stop_it.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.yeh35.android.stop_it.database.dao.DefenceUsageLogDao
import com.yeh35.android.stop_it.database.entity.DefenceUsageLog
import com.yeh35.android.stop_it.database.type.Converters

@Database(entities = [DefenceUsageLog::class]
        , exportSchema = false
        , version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun defenceUsageLogDao(): DefenceUsageLogDao

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