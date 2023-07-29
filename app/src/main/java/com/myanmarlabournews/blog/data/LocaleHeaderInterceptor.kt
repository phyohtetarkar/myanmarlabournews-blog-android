package com.myanmarlabournews.blog.data

import com.myanmarlabournews.blog.model.Post
import okhttp3.Interceptor
import okhttp3.Response

class LocaleHeaderInterceptor : Interceptor {

    private var _locale = Post.Lang.MM.name

    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().newBuilder()
            .header("X-Lang", _locale)
            .build()
        return chain.proceed(newRequest)
    }

    fun setLocale(value: String) {
        _locale = value
    }
}