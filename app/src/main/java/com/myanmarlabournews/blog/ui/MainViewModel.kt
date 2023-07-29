package com.myanmarlabournews.blog.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.myanmarlabournews.blog.model.Post
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {

    private var _darkMode = MutableStateFlow(false)

    private var _lang = MutableStateFlow(Post.Lang.MM)

    val darkMode: StateFlow<Boolean>
        get() = _darkMode

    val lang: StateFlow<Post.Lang>
        get() = _lang

    fun setDarkMode(value: Boolean) {
        _darkMode.value = value
    }

    fun setLang(value: Post.Lang) {
        _lang.value = value
    }

    companion object {
        fun provideFactory(): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MainViewModel() as T
            }
        }
    }
}