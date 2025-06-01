package com.novanest.echotrail_core

data class LogEvent(
    val timestamp: String,
    val tag: String,
    val level: LogLevel,
    val message: String,
    val durationMs: Long? = null,
    val throwable: Throwable? = null
)

enum class LogLevel {
    INFO,
    ERROR
}
