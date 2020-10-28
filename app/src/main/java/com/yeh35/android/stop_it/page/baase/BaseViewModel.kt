package com.siblingelement.location_alarm_android_app.ui.baase

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {

    protected val context = getApplication<Application>().applicationContext
    protected val scopeIo = CoroutineScope(Dispatchers.IO)
    protected val scopeMain = CoroutineScope(Dispatchers.Main)



}