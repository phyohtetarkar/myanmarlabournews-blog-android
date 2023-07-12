package com.myanmarlabournews.blog

import android.app.Application

class MyanmarLabourNewsApplication : Application() {

    private lateinit var _serviceLocator: ServiceLocator

    val serviceLocator: ServiceLocator
        get() = _serviceLocator

    override fun onCreate() {
        super.onCreate()

        _serviceLocator = DefaultServiceLocator(this)
    }

}