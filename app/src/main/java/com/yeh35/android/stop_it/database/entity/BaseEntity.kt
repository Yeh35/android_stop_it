package com.yeh35.android.stop_it.database.entity


import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import org.joda.time.DateTime

abstract class BaseEntity protected constructor() {

    /**
     * 컴파일 Task :app:kaptDebugKotlin 단계에서 필드에 getter setter를 필수로 요청하기에 public으로 작성하였지만
     * 기본적으로 직접 대입하는 것은 좋지 않은 방법이다.
     */
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    @ColumnInfo(name = "creation_date")
    lateinit var creationDate: DateTime

    @ColumnInfo(name = "modification_date")
    lateinit var modificationDate: DateTime

    fun updateDate() {
        modificationDate = DateTime.now()
    }

    fun insertDate() {
        creationDate = DateTime.now()
        modificationDate = creationDate
    }

}