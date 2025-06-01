package com.novanest.echotrail_core

class NoOpLogger : EchoLogger {
    override fun log(event: LogEvent) {
        // Do nothing
    }
}