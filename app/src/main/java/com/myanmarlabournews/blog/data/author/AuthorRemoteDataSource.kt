package com.myanmarlabournews.blog.data.author

import com.myanmarlabournews.blog.data.Resource
import com.myanmarlabournews.blog.data.convert
import com.myanmarlabournews.blog.model.Author
import com.myanmarlabournews.blog.model.Post
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthorRemoteDataSource(
    private val authorApi: AuthorApi
) {

    fun getAuthorBySlug(slug: String): Flow<Resource<Author?>> = flow {
        try {
            emit(authorApi.getAuthorBySlug(slug).convert())
        } catch (e: Exception) {
            emit(Resource.Error("Connection error"))
        }
    }

    fun getPostsByAuthor(authorId: Long, page: Int?, lang: Post.Lang?) = flow {
        try {
            emit(authorApi.getPostsByAuthor(authorId, page, lang).convert())
        } catch (e: Exception) {
            emit(Resource.Error("Connection error"))
        }
    }

}