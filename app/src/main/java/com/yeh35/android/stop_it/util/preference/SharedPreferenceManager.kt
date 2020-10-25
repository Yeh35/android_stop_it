package com.yeh35.android.stop_it.util.preference

import android.content.Context
import android.content.SharedPreferences
import com.siblingelement.location_alarm_android_app.util.preference.SharedPreferenceKey
import org.joda.time.DateTime

class SharedPreferenceManager(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE)

    fun get(key: SharedPreferenceKey): Any {
        return when(key.type) {
            SharedPreferenceType.STRING -> getString(key)
            SharedPreferenceType.BOOLEAN -> getBoolean(key)
            SharedPreferenceType.INT -> getInt(key)
            SharedPreferenceType.LONG -> getLong(key)
            SharedPreferenceType.FLOAT -> getFloat(key)
            SharedPreferenceType.DATE -> getDate(key)
            else -> {
                error("정의되지 않은 타입")
            }
        }
    }

    fun set(key: SharedPreferenceKey, value: Any) {
        when(key.type) {
            SharedPreferenceType.STRING -> setString(key, value as String)
            SharedPreferenceType.BOOLEAN -> setBoolean(key, value as Boolean)
            SharedPreferenceType.INT -> setInt(key, value as Int)
            SharedPreferenceType.LONG -> setLong(key, value as Long)
            SharedPreferenceType.FLOAT -> setFloat(key, value as Float)
            SharedPreferenceType.DATE -> setDate(key, value as DateTime)
            else -> {
                error("정의되지 않은 타입")
            }
        }
    }

    /**
     * String 값 저장
     * @param key
     * @param value
     */
    private fun setString(key: SharedPreferenceKey, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key.key, value)
        editor.apply()
    }

    /**
     * boolean 값 저장
     * @param key
     * @param value
     */
    private fun setBoolean(key: SharedPreferenceKey, value: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(key.key, value)
        editor.apply()
    }

    /**
     * int 값 저장
     * @param key
     * @param value
     */
    private fun setInt(key: SharedPreferenceKey, value: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt(key.key, value)
        editor.apply()

    }

    /**
     * long 값 저장
     * @param key
     * @param value
     */
    private fun setLong(key: SharedPreferenceKey, value: Long) {
        val editor = sharedPreferences.edit()
        editor.putLong(key.key, value)
        editor.apply()
    }

    /**
     * float 값 저장
     * @param key
     * @param value
     */
    private fun setFloat(key: SharedPreferenceKey, value: Float) {
        val editor = sharedPreferences.edit()
        editor.putFloat(key.key, value)
        editor.apply()
    }

    /**
     * Date 값 저장
     * @param key
     * @param value
     */
    private fun setDate(key: SharedPreferenceKey, value: DateTime) {
        val editor = sharedPreferences.edit()
        editor.putLong(key.key, value.millis)
        editor.apply()
    }


    /**
     * String 값 로드
     * @param key
     * @return
     */
    private fun getString(key: SharedPreferenceKey): String {
        return sharedPreferences.getString(key.key, DEFAULT_VALUE_STRING)!!
    }

    /**
     * boolean 값 로드
     * @param key
     * @return
     */
    private fun getBoolean(key: SharedPreferenceKey): Boolean {
        return sharedPreferences.getBoolean(key.key, DEFAULT_VALUE_BOOLEAN)
    }

    /**
     * int 값 로드
     * @param key
     * @return
     */
    private fun getInt(key: SharedPreferenceKey): Int {
        return sharedPreferences.getInt(key.key, DEFAULT_VALUE_INT)
    }

    /**
     * long 값 로드
     * @param key
     * @return
     */
    private fun getLong(key: SharedPreferenceKey): Long {
        return sharedPreferences.getLong(key.key, DEFAULT_VALUE_LONG)
    }

    /**
     * float 값 로드
     * @param key
     * @return
     */

    private fun getFloat(key: SharedPreferenceKey): Float {
        return sharedPreferences.getFloat(key.key, DEFAULT_VALUE_FLOAT)
    }

    /**
     * Date 값 로드
     * @param key
     */
    private fun getDate(key: SharedPreferenceKey): DateTime {
        return DateTime(sharedPreferences.getLong(key.key, DEFAULT_VALUE_LONG))
    }

    /**
     * 키 값 삭제
     * @param key
     */
    private fun removeKey(key: SharedPreferenceKey) {
        val edit = sharedPreferences.edit()
        edit.remove(key.key)
        edit.apply()
    }

    /**
     * 모든 저장 데이터 삭제
     */
    private fun clearAll() {
        val edit = sharedPreferences.edit()
        edit.clear()
        edit.apply()
    }

    companion object {
        private const val preferencesName = "local_preferences"
        private const val DEFAULT_VALUE_STRING = ""
        private const val DEFAULT_VALUE_BOOLEAN = false
        private const val DEFAULT_VALUE_INT = -1
        private const val DEFAULT_VALUE_LONG = -1L
        private const val DEFAULT_VALUE_FLOAT = -1f
    }
}