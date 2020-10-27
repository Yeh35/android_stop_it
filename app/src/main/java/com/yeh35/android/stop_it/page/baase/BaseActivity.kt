package com.yeh35.android.stop_it.page.baase

import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

abstract class BaseActivity: AppCompatActivity() {

    protected val scopeIo = CoroutineScope(Dispatchers.IO)
    protected val scopeMain = CoroutineScope(Dispatchers.Main)

}