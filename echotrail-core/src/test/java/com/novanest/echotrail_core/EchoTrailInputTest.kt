package com.novanest.echotrail_core
import org.junit.Assert.assertNotNull
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
data class LoginRequest(val email: String, val password: String)

class EchoTrailInputTest {

    private val capturedLogs = mutableListOf<LogEvent>()

    private val fakeLogger = object : EchoLogger {
        override fun log(event: LogEvent) {
            capturedLogs.add(event)
        }
    }

    @BeforeTest
    fun setup() {
        EchoTrailConfig.logger = fakeLogger
        EchoTrailConfig.isLoggingEnabled = true
        capturedLogs.clear()
    }

    @Test
    fun `logExecution logs input as data class`() {
        val input = LoginRequest("test@mail.com", "secret123")
        logExecution("LoginUseCase", input = input) {
            "Success"
        }

        val startEvent = capturedLogs.firstOrNull()
        assertNotNull(startEvent)
        assertTrue(startEvent!!.message.contains("Started with input:"))
        assertTrue(startEvent.message.contains("email=test@mail.com"))
    }

    @Test
    fun `logExecution logs input as map`() {
        val input = mapOf("userId" to 42, "retry" to true)
        logExecution("MapperLogic", input = input) {
            "Transformed"
        }

        val startEvent = capturedLogs.firstOrNull()
        assertNotNull(startEvent)
        assertTrue(startEvent!!.message.contains("Started with input:"))
        assertTrue(startEvent.message.contains("userId=42"))
        assertTrue(startEvent.message.contains("retry=true"))
    }
}
