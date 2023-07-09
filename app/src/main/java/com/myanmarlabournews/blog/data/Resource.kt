package com.myanmarlabournews.blog.data

import retrofit2.Response

sealed class Resource<T> {
    class Loading<T>() : Resource<T>()
    class Success<T>(val data: T) : Resource<T>()
    class Error<T>(val message: String) : Resource<T>()
}

fun <T> Response<T>.convert(): Resource<T> {
    return when {
        isSuccessful -> Resource.Success(body()!!)
        code() == 404 -> Resource.Error("Not found.")
        code() == 400 -> Resource.Error("Something went wrong. Please try again.")
        code() == 500 -> Resource.Error("Server error.")
        else -> Resource.Error("Something went wrong. Please try again.")
    }
}