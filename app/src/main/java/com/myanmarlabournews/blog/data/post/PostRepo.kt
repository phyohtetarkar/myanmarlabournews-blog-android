package com.myanmarlabournews.blog.data.post

import com.myanmarlabournews.blog.model.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn

class PostRepo(
    private val postRemoteDataSource: PostRemoteDataSource,
) {

    fun getPostBySlug(slug: String, lang: Post.Lang?) =
        postRemoteDataSource.getPostBySlug(slug, lang)
            .flowOn(Dispatchers.IO)

    fun getRelatedPosts(postId: Long) =
        postRemoteDataSource.getRelatedPosts(postId)
            .flowOn(Dispatchers.IO)

    fun getLatestPosts(lang: Post.Lang?) =
        postRemoteDataSource.getLatestPosts(lang)
            .flowOn(Dispatchers.IO)

    fun getPostsByType(type: Post.Type, lang: Post.Lang?) =
        postRemoteDataSource.getPostsByType(type, lang)
}