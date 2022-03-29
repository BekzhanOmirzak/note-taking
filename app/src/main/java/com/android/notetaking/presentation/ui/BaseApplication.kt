package com.android.notetaking.presentation.ui

import com.android.notetaking.BuildConfig
import com.android.notetaking.presentation.di.application.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber

/**
 * Created by bekjan on 28.03.2022.
 * email: bekjan.omirzak98@gmail.com
 */
class BaseApplication : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())

    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().bindApplication(this).build()
    }


}