package com.yeh35.android.stop_it.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import com.yeh35.android.stop_it.database.entity.BaseEntity


@Dao
abstract class AbstractBaseDao<T> where T : BaseEntity {

    @Insert
    abstract fun actualInsert(t: T): Long

    open fun insert(t: T): Long {
        t.insertDate()
        return actualInsert(t)
    }

    @Insert
    abstract fun actualInsertAll(ts: List<T>?): List<Long?>?

    open fun insertAll(ts: List<T>?): List<Long?>? {
        if (ts != null) {
            for (t in ts) {
                t.insertDate()
            }
        }
        return actualInsertAll(ts)
    }

    @Update
    abstract fun actualUpdate(t: T)

    open fun update(t: T) {
        t.updateDate()
        actualUpdate(t)
    }

    @Update
    abstract fun actualUpdateAll(ts: List<T>?)

    open fun updateAll(ts: List<T>?) {
        if (ts != null) {
            for (t in ts) {
                t.updateDate()
            }
        }
        actualUpdateAll(ts)
    }

    @Delete
    abstract fun delete(t: T)

    @Delete
    abstract fun deleteAll(ts: List<T>?)
}