package com.siblingelement.location_alarm_android_app.util.preference

import com.yeh35.android.stop_it.util.preference.SharedPreferenceType

enum class SharedPreferenceKey(val key: String, val type: SharedPreferenceType) {

    IS_DEFENSE_RUNNING("is defense running", SharedPreferenceType.BOOLEAN),
    IS_MAIN_RUNNING("is main running", SharedPreferenceType.BOOLEAN),
    ;
}