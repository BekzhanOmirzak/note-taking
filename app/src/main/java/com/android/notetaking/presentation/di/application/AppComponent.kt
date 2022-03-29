package com.android.notetaking.presentation.di.application

import android.app.Application
import android.content.Context
import androidx.navigation.NavController
import com.android.notetaking.presentation.ui.BaseApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector

/**
 * Created by bekjan on 29.03.2022.
 * email: bekjan.omirzak98@gmail.com
 */

@AppScope
@Component(modules = [AndroidInjectionModule::class, InjectBuildersModule::class, AppModule::class, ViewModelsModule::class])
interface AppComponent : AndroidInjector<BaseApplication> {


    @Component.Builder
    interface Builder {
        @BindsInstance
        fun bindApplication(application: Application): Builder


        fun build(): AppComponent
    }


}