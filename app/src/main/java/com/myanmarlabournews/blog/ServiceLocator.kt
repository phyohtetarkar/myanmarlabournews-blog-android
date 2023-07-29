package com.myanmarlabournews.blog

import android.content.Context
import com.myanmarlabournews.blog.data.RetrofitManager
import com.myanmarlabournews.blog.data.author.AuthorApi
import com.myanmarlabournews.blog.data.author.AuthorRemoteDataSource
import com.myanmarlabournews.blog.data.author.AuthorRepo
import com.myanmarlabournews.blog.data.post.PostApi
import com.myanmarlabournews.blog.data.post.PostRemoteDataSource
import com.myanmarlabournews.blog.data.post.PostRepo
import com.myanmarlabournews.blog.data.tag.TagApi
import com.myanmarlabournews.blog.data.tag.TagRemoteDataSource
import com.myanmarlabournews.blog.data.tag.TagRepo

interface ServiceLocator {
    val retrofitManager: RetrofitManager

    val postRepo: PostRepo

    val authorRepo: AuthorRepo

    val tagRepo: TagRepo
}

class DefaultServiceLocator(context: Context) : ServiceLocator {

    private var _retrofitManager: RetrofitManager

    private var _postRepo: PostRepo

    private var _authorRepo: AuthorRepo

    private var _tagRepo: TagRepo

    init {
        _retrofitManager = RetrofitManager(context)
        _postRepo = PostRepo(PostRemoteDataSource(_retrofitManager.create(PostApi::class.java)))
        _authorRepo =
            AuthorRepo(AuthorRemoteDataSource(_retrofitManager.create(AuthorApi::class.java)))
        _tagRepo = TagRepo(TagRemoteDataSource(_retrofitManager.create(TagApi::class.java)))
    }

    override val retrofitManager: RetrofitManager
        get() = _retrofitManager

    override val postRepo: PostRepo
        get() = _postRepo

    override val authorRepo: AuthorRepo
        get() = _authorRepo

    override val tagRepo: TagRepo
        get() = _tagRepo

}