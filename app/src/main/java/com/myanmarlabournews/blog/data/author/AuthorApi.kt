package com.myanmarlabournews.blog.data.author

import com.myanmarlabournews.blog.model.Author
import com.myanmarlabournews.blog.model.Page
import com.myanmarlabournews.blog.model.Post
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface AuthorApi {

    @GET("v1/authors/{slug}")
    suspend fun getAuthorBySlug(@Path("slug") slug: String): Response<Author?>

    @GET("v1/authors/{id}/posts")
    suspend fun getPostsByAuthor(
        @Path("id") authorId: Long,
        @Query("page") page: Int?,
        @Header("X-Lang") lang: Post.Lang?
    ): Response<Page<Post>>

}