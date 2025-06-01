package com.novanest.echotrail_core

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class EchoTrailTest {

    private val capturedLogs = mutableListOf<LogEvent>()

    private val fakeLogger = object : EchoLogger {
        override fun log(event: LogEvent) {
            capturedLogs.add(event)
        }
    }

    @Test
    fun `logExecution logs start and success correctly`() {
        EchoTrailConfig.logger = fakeLogger
        EchoTrailConfig.isLoggingEnabled = true

        val result = logExecution("TestTag", input = "input-data") {
            "result-data"
        }

        assertEquals("result-data", result)
        assertEquals(2, capturedLogs.size)

        val startEvent = capturedLogs[0]
        val successEvent = capturedLogs[1]

        assertEquals(LogLevel.INFO, startEvent.level)
        assertTrue(startEvent.message.contains("Started with input"))

        assertEquals(LogLevel.INFO, successEvent.level)
        assertTrue(successEvent.message.contains("Success with result"))
        assertTrue(successEvent.durationMs != null && successEvent.durationMs!! >= 0)
    }

    @Test
    fun `logExecution logs error correctly`() {
        EchoTrailConfig.logger = fakeLogger
        EchoTrailConfig.isLoggingEnabled = true

        capturedLogs.clear()

        val exception = kotlin.runCatching {
            logExecution("ErrorTag", input = "bad-input") {
                throw IllegalStateException("Something went wrong")
            }
        }.exceptionOrNull()

        assertTrue(exception is IllegalStateException)
        assertEquals(2, capturedLogs.size)

        val errorEvent = capturedLogs[1]
        assertEquals(LogLevel.ERROR, errorEvent.level)
        assertTrue(errorEvent.message.contains("Failed with error"))
        assertTrue(errorEvent.throwable is IllegalStateException)
    }
}
