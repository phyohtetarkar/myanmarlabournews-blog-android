package com.myanmarlabournews.blog.data.post

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.myanmarlabournews.blog.data.author.AuthorApi
import com.myanmarlabournews.blog.data.convertToBody
import com.myanmarlabournews.blog.model.Post

class PostsByAuthorPagingSource(
    private val authorId: Long,
    private val lang: Post.Lang?,
    private val authorApi: AuthorApi,
) : PagingSource<Int, Post>() {
    override fun getRefreshKey(state: PagingState<Int, Post>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Post> {
        return try {
            val page = params.key
            val resp = authorApi.getPostsByAuthor(authorId, page, lang)
            val body = resp.convertToBody() ?: throw RuntimeException("No data found")
            LoadResult.Page(
                data = body.list,
                prevKey = null,
                nextKey = if (body.list.isEmpty()) null else body.currentPage.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}