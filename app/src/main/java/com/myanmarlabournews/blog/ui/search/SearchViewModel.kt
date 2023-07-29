package com.myanmarlabournews.blog.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.myanmarlabournews.blog.data.Resource
import com.myanmarlabournews.blog.data.tag.TagRepo
import com.myanmarlabournews.blog.model.Tag
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


data class SearchUiState(
    val isLoading: Boolean,
    val tags: List<Tag>,
    val errorMessage: String?
) {
    companion object {
        fun loading(): SearchUiState {
            return SearchUiState(
                isLoading = true,
                tags = listOf(),
                errorMessage = null
            )
        }
    }
}

class SearchViewModel(private val tagRepo: TagRepo) : ViewModel() {

    var launched = false

    private var _state = MutableStateFlow(SearchUiState.loading())

    val state: StateFlow<SearchUiState>
        get() = _state

    fun loadTags() = viewModelScope.launch {
        _state.update { it.copy(isLoading = true, errorMessage = null) }
        tagRepo.getTags().collect { resource ->
            _state.update {
                when (resource) {
                    is Resource.Success -> SearchUiState(
                        isLoading = false,
                        tags = resource.data,
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
            tagRepo: TagRepo
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return SearchViewModel(tagRepo) as T
            }
        }
    }

}