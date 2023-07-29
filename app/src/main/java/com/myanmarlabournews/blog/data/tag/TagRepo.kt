package com.myanmarlabournews.blog.data.tag

import com.myanmarlabournews.blog.data.Resource
import com.myanmarlabournews.blog.model.Post
import com.myanmarlabournews.blog.model.Tag
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class TagRepo(
    private val tagRemoteDataSource: TagRemoteDataSource
) {

    fun getTagBySlug(slug: String) = tagRemoteDataSource.getTagBySlug(slug)
        .flowOn(Dispatchers.IO)

    fun getTags(): Flow<Resource<List<Tag>>> =
        tagRemoteDataSource.getTags()
            .flowOn(Dispatchers.IO)

    fun getPostsByTag(tagId: Int, page: Int?, lang: Post.Lang?) =
        tagRemoteDataSource.getPostsByTag(tagId, page, lang)
            .flowOn(Dispatchers.IO)
}