package com.myanmarlabournews.blog.data.tag

import com.myanmarlabournews.blog.data.Resource
import com.myanmarlabournews.blog.data.convert
import com.myanmarlabournews.blog.model.Post
import com.myanmarlabournews.blog.model.Tag
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TagRemoteDataSource(
    private val tagApi: TagApi
) {

    fun getTagBySlug(slug: String): Flow<Resource<Tag?>> = flow {
        try {
            emit((tagApi.getTagBySlug(slug)).convert())
        } catch (e: Exception) {
            emit(Resource.Error("Connection error."))
        }

    }

    fun getTags(): Flow<Resource<List<Tag>>> = flow {
        try {
            emit(tagApi.getTags().convert())
        } catch (e: Exception) {
            emit(Resource.Error("Connection error."))
        }
    }

    fun getPostsByTag(tagId: Int, page: Int?, lang: Post.Lang?) = flow {
        try {
            emit(tagApi.getPostsByTag(tagId, page, lang).convert())
        } catch (e: Exception) {
            emit(Resource.Error("Connection error."))
        }
    }

}