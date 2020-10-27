package com.yeh35.android.stop_it.database.dao

import com.yeh35.android.stop_it.BaseTest
import com.yeh35.android.stop_it.database.AppDatabase
import com.yeh35.android.stop_it.database.entity.DefenceUsageLog
import org.junit.Assert
import org.junit.Test

class PhoneUsageLogDaoTest : BaseTest() {

    @Test
    fun defenceLog() {
        val defenceUsageLogDao = AppDatabase.getInstance(getContext()).defenceUsageLogDao()

        val beforeCount = defenceUsageLogDao.getAll().size

        var usageLog = DefenceUsageLog()
        val usageLogId = defenceUsageLogDao.insert(usageLog)
        usageLog = defenceUsageLogDao.getFindId(usageLogId)

        Assert.assertEquals(false, usageLog.waited)
        Assert.assertEquals(beforeCount + 1, defenceUsageLogDao.getAll().size)

        usageLog.waited()
        Thread.sleep(100)
        defenceUsageLogDao.update(usageLog)

        val usageLogs = defenceUsageLogDao.getAll()
        val updatedUsageLog = usageLogs[usageLogs.size - 1]

        println("creationDate : ${updatedUsageLog.creationDate.millis}")
        println("modificationDate : ${updatedUsageLog.modificationDate.millis}")

        Assert.assertEquals(beforeCount + 1, usageLogs.size)
        Assert.assertEquals(true, updatedUsageLog.waited)
        Assert.assertNotEquals(updatedUsageLog.creationDate.millis, updatedUsageLog.modificationDate.millis)

    }
}
