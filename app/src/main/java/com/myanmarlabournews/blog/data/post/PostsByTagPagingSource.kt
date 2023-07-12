package com.myanmarlabournews.blog.data.post

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.myanmarlabournews.blog.data.convertToBody
import com.myanmarlabournews.blog.data.tag.TagApi
import com.myanmarlabournews.blog.model.Post

class PostsByTagPagingSource(
    private val tagId: Int,
    private val lang: Post.Lang,
    private val tagApi: TagApi,
) : PagingSource<Int, Post>() {
    override fun getRefreshKey(state: PagingState<Int, Post>): Int? {
        // Try to find the page key of the closest page to anchorPosition, from
        // either the prevKey or the nextKey, but you need to handle nullability
        // here:
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey null -> anchorPage is the initial page, so
        //    just return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Post> {
        return try {
            val page = params.key
            val resp = tagApi.getPostsByTag(tagId, page, lang)
            val body = resp.convertToBody()
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