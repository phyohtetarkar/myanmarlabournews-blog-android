package com.myanmarlabournews.blog.data.tag

import com.myanmarlabournews.blog.model.Page
import com.myanmarlabournews.blog.model.Post
import com.myanmarlabournews.blog.model.Tag
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface TagApi {

    @GET("v2/tags/{slug}")
    suspend fun getTagBySlug(@Path("slug") slug: String): Response<Tag?>

    @GET("v2/tags")
    suspend fun getTags(): Response<List<Tag>>

    @GET("v2/tags/{id}/posts")
    suspend fun getPostsByTag(
        @Path("id") id: Int,
        @Query("page") page: Int?,
        @Header("X-Lang") lang: Post.Lang?
    ): Response<Page<Post>>

}