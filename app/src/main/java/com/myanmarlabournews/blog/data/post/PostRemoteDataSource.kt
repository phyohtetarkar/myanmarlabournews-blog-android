package com.myanmarlabournews.blog.data.post

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.myanmarlabournews.blog.data.Resource
import com.myanmarlabournews.blog.data.convert
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
            emit(Resource.Error("Connection error."))
        }
    }

    fun getRelatedPosts(postId: Long): Flow<Resource<List<Post>>> = flow {
        try {
            emit(postApi.getRelatedPosts(postId).convert())
        } catch (e: Exception) {
            emit(Resource.Error("Connection error."))
        }
    }

    fun getLatestPosts(lang: Post.Lang?): Flow<Resource<List<Post>>> = flow {
        try {
            emit(postApi.getLatestPosts(lang).convert())
        } catch (e: Exception) {
            emit(Resource.Error("Connection error."))
        }
    }

    fun getPostsByType(type: Post.Type, lang: Post.Lang?) = Pager(
        config = PagingConfig(
            pageSize = 15
        ),
        pagingSourceFactory = {
            PostsByTypePagingSource(type, lang ?: Post.Lang.MM, postApi)
        }
    ).flow

}