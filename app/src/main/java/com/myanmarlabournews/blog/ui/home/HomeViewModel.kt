package com.myanmarlabournews.blog.ui.home

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

data class HomeUiState(
    val isLoading: Boolean,
    val posts: List<Post>,
    val errorMessage: String?
) {
    companion object {
        fun loading(): HomeUiState {
            return HomeUiState(
                isLoading = true,
                posts = listOf(),
                errorMessage = null
            )
        }
    }
}

class HomeViewModel(private val postRepo: PostRepo) : ViewModel() {

    var launched = false

    var locale: Post.Lang? = null

    private var _state = MutableStateFlow(HomeUiState.loading())

    val state: StateFlow<HomeUiState>
        get() = _state

    fun loadPosts(lang: Post.Lang) = viewModelScope.launch {
        _state.update { it.copy(isLoading = true, errorMessage = null) }
        postRepo.getLatestPosts(lang).collect { resource ->
            _state.update {
                when (resource) {
                    is Resource.Success -> HomeUiState(
                        isLoading = false,
                        posts = resource.data,
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
                return HomeViewModel(postRepo) as T
            }
        }
    }

}