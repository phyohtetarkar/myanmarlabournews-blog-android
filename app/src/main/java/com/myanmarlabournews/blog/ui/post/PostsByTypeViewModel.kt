package com.myanmarlabournews.blog.ui.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.myanmarlabournews.blog.data.Resource
import com.myanmarlabournews.blog.data.post.PostRepo
import com.myanmarlabournews.blog.model.Post
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class PostListUiState(
    val posts: List<Post> = listOf(),
    val isRefreshing: Boolean = false,
    val isAppending: Boolean = false,
    val currentPage: Int = 0,
    val totalPage: Int = 0,
    val refreshError: String? = null,
    val paginateError: String? = null
)

class PostsByTypeViewModel(
    private val postRepo: PostRepo,
) : ViewModel() {

    var launched = false

    private var _state = MutableStateFlow(PostListUiState(isRefreshing = true))

    val state: StateFlow<PostListUiState>
        get() = _state

    fun loadPosts(type: Post.Type, page: Int?, lang: Post.Lang?, paginate: Boolean = false) =
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isRefreshing = !paginate,
                    isAppending = paginate,
                    refreshError = null,
                    paginateError = null
                )
            }
            postRepo.getPostsByType(type, page, lang).collect { resource ->
                _state.update {
                    when (resource) {
                        is Resource.Success -> if (paginate) {
                            PostListUiState(
                                posts = it.posts.plus(resource.data.list),
                                currentPage = resource.data.currentPage,
                                totalPage = resource.data.totalPage
                            )
                        } else {
                            PostListUiState(
                                posts = resource.data.list,
                                currentPage = resource.data.currentPage,
                                totalPage = resource.data.totalPage
                            )
                        }

                        is Resource.Error -> if (paginate) {
                            it.copy(
                                isRefreshing = false,
                                isAppending = false,
                                paginateError = resource.message
                            )
                        } else {
                            it.copy(
                                isRefreshing = false,
                                isAppending = false,
                                refreshError = resource.message
                            )
                        }

                        else -> PostListUiState()
                    }
                }
            }
        }

    companion object {
        fun provideFactory(
            postRepo: PostRepo,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return PostsByTypeViewModel(postRepo) as T
            }
        }
    }

}