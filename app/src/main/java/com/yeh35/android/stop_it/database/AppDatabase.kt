package com.yeh35.android.stop_it.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

//@Database(entities = [], version = 1)
//@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

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