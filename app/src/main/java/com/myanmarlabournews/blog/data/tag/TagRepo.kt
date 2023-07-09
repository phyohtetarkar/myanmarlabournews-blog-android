package com.myanmarlabournews.blog.data.tag

import com.myanmarlabournews.blog.data.Resource
import com.myanmarlabournews.blog.model.Tag
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn

class TagRepo(
    private val tagRemoteDataSource: TagRemoteDataSource
) {

    fun getTags(): Flow<Resource<List<Tag>>> =
        tagRemoteDataSource.getTags()
            .flowOn(Dispatchers.IO)
            .catch { e ->
                emit(Resource.Error(e.message ?: "Connection error."))
            }

}