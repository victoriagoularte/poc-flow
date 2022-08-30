package com.example.flow.common

import androidx.annotation.VisibleForTesting
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Modifier.PRIVATE

@VisibleForTesting(otherwise = PRIVATE)
fun createRetrofit(
    baseUrl: String,
    okHttpClient: OkHttpClient? = null,
): Retrofit {
    val retrofitBuilder = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
    okHttpClient?.let { retrofitBuilder.client(okHttpClient) }

    return retrofitBuilder.build()
}


