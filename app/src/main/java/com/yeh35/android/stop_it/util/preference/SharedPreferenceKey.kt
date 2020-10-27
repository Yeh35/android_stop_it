package com.yeh35.android.stop_it.util.preference

enum class SharedPreferenceKey(val key: String, val type: SharedPreferenceType) {

    IS_DEFENSE_RUNNING("is defense running", SharedPreferenceType.BOOLEAN),
    IS_MAIN_RUNNING("is main running", SharedPreferenceType.BOOLEAN),
    ;
}