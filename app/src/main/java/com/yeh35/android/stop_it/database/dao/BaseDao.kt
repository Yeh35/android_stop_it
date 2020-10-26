package com.yeh35.android.stop_it.database.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import com.yeh35.android.stop_it.database.entity.BaseEntity
import io.reactivex.Completable
import io.reactivex.Maybe
import org.joda.time.DateTime

interface BaseDao<T> where T : BaseEntity {

    fun getAll(): List<T>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun actualInsert(obj: T): Maybe<Long>

    @Update(onConflict = OnConflictStrategy.ABORT)
    fun actualUpdate(obj: T)

    @Delete
    fun delete(obj: T): Completable

    companion object {

        open class DAOWrapper<P, T>(private val daoInstance: T) where T : BaseDao<P>, P : BaseEntity {
            fun insertWithTimeStepData(modelData: P) {
                modelData.modificationDate = DateTime.now()
                this.daoInstance.actualInsert(modelData)
            }

            fun updateWithTimeStepData(modelData: P) {
                modelData.modificationDate = DateTime.now()
                this.daoInstance.actualInsert(modelData)
            }
        }
    }
}