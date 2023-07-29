package com.myanmarlabournews.blog.data.post

import com.myanmarlabournews.blog.model.Page
import com.myanmarlabournews.blog.model.Post
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface PostApi {

    @GET("v2/posts/{slug}")
    suspend fun getPostBySlug(
        @Path("slug") slug: String,
        @Header("X-Lang") lang: Post.Lang?
    ): Response<Post?>

    @GET("v2/posts/{postId}/related")
    suspend fun getRelatedPosts(@Path("postId") postId: Long): Response<List<Post>>

    @GET("v2/posts/latest")
    suspend fun getLatestPosts(@Header("X-Lang") lang: Post.Lang?): Response<List<Post>>

    @GET("v2/posts")
    suspend fun getPosts(
        @Query("type") type: Post.Type?,
        @Query("page") page: Int?,
        @Header("X-Lang") lang: Post.Lang?
    ): Response<Page<Post>>
}