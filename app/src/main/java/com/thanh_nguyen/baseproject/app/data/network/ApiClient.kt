package com.thanh_nguyen.baseproject.app.data.network

import com.thanh_nguyen.baseproject.BuildConfig
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {
    companion object{
        inline fun <reified T> createService(): T{
            val okkHttpClient = OkHttpClient.Builder()
                    .addInterceptor(AuthInterceptor())
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .connectionPool(ConnectionPool(0, 5, TimeUnit.MINUTES))
                    .protocols(listOf(Protocol.HTTP_1_1))


            if (BuildConfig.DEBUG) {
                val loggingInterceptor = HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
                okkHttpClient.addInterceptor(loggingInterceptor)
            }

            return Retrofit.Builder()
                    .client(okkHttpClient.build())
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(T::class.java)
        }
    }
}