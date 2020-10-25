package com.siblingelement.location_alarm_android_app.ui.baase

import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

abstract class BaseActivity: AppCompatActivity() {

    protected val scopeIo = CoroutineScope(Dispatchers.IO)
    protected val scopeMain = CoroutineScope(Dispatchers.Main)



}