package com.yeh35.android.stop_it.database.entity


import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import org.joda.time.DateTime

abstract class BaseEntity protected constructor() {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    @ColumnInfo(name = "creation_date")
    var creationDate: DateTime = DateTime.now()

    @ColumnInfo(name = "modification_date")
    var modificationDate: DateTime = DateTime.now()
}