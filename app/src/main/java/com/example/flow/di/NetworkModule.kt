package com.example.flow.di

import com.example.flow.BuildConfig
import com.example.flow.data.service.PokeService
import com.example.flow.network.DefaultQueryParameterInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    factory { DefaultQueryParameterInterceptor() }
    factory { providesHttpLoggingInterceptor() }
    factory { provideOkHttpClient(get(), get()) }
    single { provideRetrofit(get()) }
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BuildConfig.API_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun provideOkHttpClient(
    defaultInterceptor: DefaultQueryParameterInterceptor,
    loggingInterceptor: HttpLoggingInterceptor
): OkHttpClient {
    return OkHttpClient()
        .newBuilder()
        .addInterceptor(defaultInterceptor)
        .addInterceptor(loggingInterceptor)
        .build()
}

fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor()
    .apply {
        level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else
            HttpLoggingInterceptor.Level.NONE
    }

fun provideService(retrofit: Retrofit): PokeService =
    retrofit.create(PokeService::class.java)