package com.myanmarlabournews.blog.ui

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.preference.PreferenceManager
import com.myanmarlabournews.blog.MyanmarLabourNewsApplication
import com.myanmarlabournews.blog.ServiceLocator
import com.myanmarlabournews.blog.model.Post
import com.myanmarlabournews.blog.util.KEY_DARK_MODE

class MainActivity : ComponentActivity() {

    private lateinit var _sharePreference: SharedPreferences

    private val mainViewModel: MainViewModel by viewModels()
    private val serviceLocator: ServiceLocator
        get() = (application as MyanmarLabourNewsApplication).serviceLocator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _sharePreference = PreferenceManager.getDefaultSharedPreferences(this)

        val darkMode = _sharePreference.getBoolean(KEY_DARK_MODE, false)

        mainViewModel.setDarkMode(darkMode)

        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colors.background
            ) {
                MainApp(
                    serviceLocator = serviceLocator,
                    mainViewModel = mainViewModel
                )
            }
        }
    }

    fun toggleDarkMode(value: Boolean) {
        mainViewModel.setDarkMode(value)
        _sharePreference.edit().putBoolean(KEY_DARK_MODE, value).apply()
    }

    fun toggleLocale(value: Post.Lang) {
        mainViewModel.setLang(value)
    }
}