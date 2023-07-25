package com.myanmarlabournews.blog.data.author

import com.myanmarlabournews.blog.model.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn

class AuthorRepo(
    private val authorRemoteDataSource: AuthorRemoteDataSource
) {

    fun getAuthorBySlug(slug: String) = authorRemoteDataSource.getAuthorBySlug(slug)
        .flowOn(Dispatchers.IO)

    fun getPostsByAuthor(authorId: Long, page: Int?, lang: Post.Lang?) =
        authorRemoteDataSource.getPostsByAuthor(authorId, page, lang)
            .flowOn(Dispatchers.IO)
}