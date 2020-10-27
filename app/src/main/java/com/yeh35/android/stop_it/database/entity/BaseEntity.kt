package com.yeh35.android.stop_it.database.entity


import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import org.joda.time.DateTime

abstract class BaseEntity protected constructor() {
    @PrimaryKey(autoGenerate = true)
    private var id: Long = 0

    @ColumnInfo(name = "creation_date")
    private lateinit var creationDate: DateTime

    @ColumnInfo(name = "modification_date")
    private lateinit var modificationDate: DateTime

    fun getId(): Long {
        return id
    }

    fun getCreationDate(): DateTime {
        return creationDate
    }

    fun getModificationDate(): DateTime {
        return modificationDate
    }

    fun updateDate() {
        modificationDate = DateTime.now()
    }

    fun insertDate() {
        creationDate = DateTime.now()
        modificationDate = DateTime.now()
    }

}