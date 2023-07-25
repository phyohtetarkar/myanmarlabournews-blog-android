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

data class PostDetailUiState(
    val isLoading: Boolean,
    val post: Post? = null,
    val errorMessage: String? = null
) {
    companion object {
        fun loading(): PostDetailUiState {
            return PostDetailUiState(
                isLoading = true,
            )
        }
    }
}

class PostDetailViewModel(private val postRepo: PostRepo) : ViewModel() {

    private var _state = MutableStateFlow(PostDetailUiState.loading())

    val state: StateFlow<PostDetailUiState>
        get() = _state

    fun loadPost(slug: String) = viewModelScope.launch {
        _state.update { it.copy(isLoading = true, errorMessage = null) }
        postRepo.getPostBySlug(slug, null).collect { resource ->
            _state.update {
                when (resource) {
                    is Resource.Success -> PostDetailUiState(
                        isLoading = false,
                        post = resource.data,
                        errorMessage = null
                    )

                    is Resource.Error -> it.copy(isLoading = false, errorMessage = resource.message)
                    else -> it.copy(isLoading = true, errorMessage = null)
                }
            }
        }
    }

    companion object {
        fun provideFactory(
            postRepo: PostRepo
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return PostDetailViewModel(postRepo) as T
            }
        }
    }
}