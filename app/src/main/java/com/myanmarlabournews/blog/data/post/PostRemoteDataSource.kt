package com.myanmarlabournews.blog.data.post

import com.myanmarlabournews.blog.data.Resource
import com.myanmarlabournews.blog.data.convert
import com.myanmarlabournews.blog.model.Page
import com.myanmarlabournews.blog.model.Post
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PostRemoteDataSource(
    private val postApi: PostApi
) {

    fun getPostBySlug(slug: String, lang: Post.Lang?): Flow<Resource<Post?>> = flow {
        try {
            emit(postApi.getPostBySlug(slug, lang).convert())
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error("Connection error"))
        }
    }

    fun getRelatedPosts(postId: Long): Flow<Resource<List<Post>>> = flow {
        try {
            emit(postApi.getRelatedPosts(postId).convert())
        } catch (e: Exception) {
            emit(Resource.Error("Connection error"))
        }
    }

    fun getLatestPosts(lang: Post.Lang?): Flow<Resource<List<Post>>> = flow {
        try {
            emit(postApi.getLatestPosts(lang).convert())
        } catch (e: Exception) {
            emit(Resource.Error("Connection error"))
        }
    }

    fun getPostsByType(type: Post.Type, page: Int?, lang: Post.Lang?): Flow<Resource<Page<Post>>> =
        flow {
            try {
                emit(postApi.getPosts(type, page, lang).convert())
            } catch (e: Exception) {
                emit(Resource.Error("Connection error"))
            }
        }

}