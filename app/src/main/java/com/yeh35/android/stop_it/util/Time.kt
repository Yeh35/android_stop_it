package com.yeh35.android.stop_it.util

class Time(hours: Int, minutes: Int, seconds: Int) {

    private var seconds: Long

    init {
        this.seconds = hours * HOUR_IN_SECONDS
        this.seconds += minutes * MINUTE_IN_SECONDS
        this.seconds += seconds
    }

    companion object {
        private const val HOUR_IN_SECONDS: Long = 60 * 60
        private const val MINUTE_IN_SECONDS: Long = 60

        val oneSecond = Time(0, 0, 1)
    }

    operator fun minusAssign(time: Time) {
        seconds -= time.seconds
    }

    operator fun plusAssign(time: Time) {
        seconds += time.seconds
    }

}