package com.myanmarlabournews.blog.data.tag

import com.myanmarlabournews.blog.data.Resource
import com.myanmarlabournews.blog.data.convert
import com.myanmarlabournews.blog.model.Tag
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TagRemoteDataSource(
    private val tagApi: TagApi
) {

    fun getTags(): Flow<Resource<List<Tag>>> = flow {
        emit(tagApi.getTags().convert())
    }

}