package com.novanest.echotrail_core

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

inline fun <T> logExecution(
    tag: String,
    input: Any? = null,
    block: () -> T
): T {
    if (!EchoTrailConfig.isLoggingEnabled) return block()

    val startTime = System.currentTimeMillis()
    val timestamp = currentTimestamp()

    EchoTrailConfig.logger.log(
        LogEvent(
            timestamp = timestamp,
            tag = tag,
            level = LogLevel.INFO,
            message = "Started with input: $input"
        )
    )

    return try {
        val result = block()
        val duration = System.currentTimeMillis() - startTime
        EchoTrailConfig.logger.log(
            LogEvent(
                timestamp = currentTimestamp(),
                tag = tag,
                level = LogLevel.INFO,
                message = "Success with result: $result",
                durationMs = duration
            )
        )
        result
    } catch (e: Exception) {
        val duration = System.currentTimeMillis() - startTime
        EchoTrailConfig.logger.log(
            LogEvent(
                timestamp = currentTimestamp(),
                tag = tag,
                level = LogLevel.ERROR,
                message = "Failed with error: ${e.localizedMessage}",
                durationMs = duration,
                throwable = e
            )
        )
        throw e
    }
}

suspend inline fun <T> logExecutionSuspend(
    tag: String,
    input: Any? = null,
    crossinline block: suspend () -> T
): T {
    if (!EchoTrailConfig.isLoggingEnabled) return block()

    val startTime = System.currentTimeMillis()
    val timestamp = currentTimestamp()

    EchoTrailConfig.logger.log(
        LogEvent(
            timestamp = timestamp,
            tag = tag,
            level = LogLevel.INFO,
            message = "Started with input: $input"
        )
    )

    return try {
        val result = block()
        val duration = System.currentTimeMillis() - startTime
        EchoTrailConfig.logger.log(
            LogEvent(
                timestamp = currentTimestamp(),
                tag = tag,
                level = LogLevel.INFO,
                message = "Success with result: $result",
                durationMs = duration
            )
        )
        result
    } catch (e: Exception) {
        val duration = System.currentTimeMillis() - startTime
        EchoTrailConfig.logger.log(
            LogEvent(
                timestamp = currentTimestamp(),
                tag = tag,
                level = LogLevel.ERROR,
                message = "Failed with error: ${e.localizedMessage}",
                durationMs = duration,
                throwable = e
            )
        )
        throw e
    }
}


@PublishedApi
internal fun currentTimestamp(): String {
    return SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
}
