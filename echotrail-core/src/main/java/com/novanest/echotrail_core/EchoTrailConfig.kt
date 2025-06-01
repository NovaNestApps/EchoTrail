package com.novanest.echotrail_core

object EchoTrailConfig {
    var isLoggingEnabled: Boolean = true
    var logger: EchoLogger = LogcatLogger()
}
