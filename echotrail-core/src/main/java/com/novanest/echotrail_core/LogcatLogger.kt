package com.novanest.echotrail_core

import android.util.Log

class LogcatLogger : EchoLogger {
    override fun log(event: LogEvent) {
        val logMessage = buildString {
            append("[${event.timestamp}] ")
            append("[${event.level}] ")
            append("[${event.tag}] ")
            append(event.message)
            if (event.durationMs != null) append(" in ${event.durationMs}ms")
            if (event.throwable != null) append(" with error: ${event.throwable.localizedMessage}")
        }

        when (event.level) {
            LogLevel.INFO -> Log.d(event.tag, logMessage)
            LogLevel.ERROR -> Log.e(event.tag, logMessage, event.throwable)
        }
    }
}