package com.example.flow.common

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import okio.buffer
import okio.source

fun MockWebServer.enqueueResponse(fileName: String? = null, code: Int) {
    javaClass.classLoader?.let {
        val inputStream = it.getResourceAsStream("api-response/$fileName")
        val source = inputStream.source().buffer()
        dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return MockResponse()
                    .setBody(source.readString(Charsets.UTF_8))
                    .setResponseCode(code)
            }
        }
    }
}

fun dispatchRequest(requestMock: RequestMock.() -> Unit): RequestMock {
    return RequestMock().apply(requestMock)
}

infix fun Int.with(path: String) = RequestMock(code = this, path = path)

infix fun RequestMock.startOn(mockWebServer: MockWebServer) {
    mockWebServer.enqueueResponse(path, code)
}


