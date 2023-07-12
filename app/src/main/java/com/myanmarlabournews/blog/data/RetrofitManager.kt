package com.myanmarlabournews.blog.data

import android.content.Context
import com.myanmarlabournews.blog.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

class RetrofitManager(context: Context) {

    private val okHttpClient: OkHttpClient
    private val retrofit: Retrofit
    private val localeHeaderInterceptor: LocaleHeaderInterceptor

    init {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC

        localeHeaderInterceptor = LocaleHeaderInterceptor()

        okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            //.addInterceptor(localeHeaderInterceptor)
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .client(okHttpClient)
            .addConverterFactory(JacksonConverterFactory.create())
            .build()
    }

    fun <T> create(clazz: Class<T>): T = retrofit.create(clazz)

    fun setLocale(value: String) {
        localeHeaderInterceptor.setLocale(value)
    }

}