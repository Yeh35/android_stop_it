package com.yeh35.android.stop_it

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
open class BaseTest {

    private lateinit var appContext: Context

    protected fun getContext() : Context {
        return if (this::appContext.isInitialized) {
            appContext
        } else {
            val appContext = InstrumentationRegistry.getInstrumentation().targetContext
            Assert.assertEquals("com.yeh35.android.stop_it", appContext.packageName)
            appContext
        }
    }

}