package com.example.flow.common

import okhttp3.mockwebserver.MockWebServer
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class MockWebServerTestRule : TestWatcher() {

    val server = MockWebServer()
    private val baseUrl = server.url("/").toString()

    override fun starting(description: Description) {
        super.starting(description)
    }

    override fun finished(description: Description) {
        server.shutdown()
    }

    fun retrofit() = createRetrofit(baseUrl)
}
