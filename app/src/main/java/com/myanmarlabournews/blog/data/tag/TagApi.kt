package com.myanmarlabournews.blog.data.tag

import com.myanmarlabournews.blog.model.Tag
import retrofit2.Response
import retrofit2.http.GET

interface TagApi {

    @GET("v2/tags")
    suspend fun getTags(): Response<List<Tag>>

}