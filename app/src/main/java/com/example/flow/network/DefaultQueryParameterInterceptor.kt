package com.example.flow.network

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

private const val API_KEY = "api_key"

class DefaultQueryParameterInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestOriginal: Request = chain.request()
        val url: HttpUrl = requestOriginal.url
            .newBuilder()
//            .addQueryParameter(API_KEY, "some_key")
            .build()
        val request = requestOriginal.newBuilder().url(url).build()
        return chain.proceed(request)
    }
}