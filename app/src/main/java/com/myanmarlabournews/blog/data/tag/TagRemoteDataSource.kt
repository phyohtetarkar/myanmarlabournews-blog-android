package com.myanmarlabournews.blog.data.tag

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.myanmarlabournews.blog.data.Resource
import com.myanmarlabournews.blog.data.convert
import com.myanmarlabournews.blog.data.post.PostsByTagPagingSource
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

    fun getPostsByTag(tagInt: Int, lang: Post.Lang?) = Pager(
        config = PagingConfig(
            pageSize = 15
        ),
        pagingSourceFactory = {
            PostsByTagPagingSource(tagInt, lang ?: Post.Lang.MM, tagApi)
        }
    ).flow

}