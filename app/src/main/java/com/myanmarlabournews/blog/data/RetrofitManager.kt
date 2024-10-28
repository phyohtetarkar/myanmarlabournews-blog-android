package com.myanmarlabournews.blog.data

import android.content.Context
import com.myanmarlabournews.blog.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.lang.reflect.Type

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

        val factory = JacksonConverterFactory.create()

        val customConverter = object : Converter.Factory() {
            override fun responseBodyConverter(
                type: Type,
                annotations: Array<out Annotation>,
                retrofit: Retrofit
            ): Converter<ResponseBody, *>? {
                val delegate = factory.responseBodyConverter(type, annotations, retrofit)
                return Converter {
                    if (it.contentLength() == 0L) {
                        return@Converter null
                    }

                    return@Converter delegate?.convert(it)
                }
            }
        }

        retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .client(okHttpClient)
            .addConverterFactory(customConverter)
            .addConverterFactory(JacksonConverterFactory.create())
            .build()
    }

    fun <T> create(clazz: Class<T>): T = retrofit.create(clazz)

    fun setLocale(value: String) {
        localeHeaderInterceptor.setLocale(value)
    }

}