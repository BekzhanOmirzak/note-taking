package com.android.notetaking.presentation.di.application

import com.android.notetaking.presentation.ui.MainActivity
import com.android.notetaking.presentation.ui.details.NoteDetailsFragment
import com.android.notetaking.presentation.ui.home.HomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by bekjan on 29.03.2022.
 * email: bekjan.omirzak98@gmail.com
 */

@Module
abstract class InjectBuildersModule {

    @ContributesAndroidInjector
    abstract fun injectMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun injectHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun injectNoteDetailsFragment(): NoteDetailsFragment


}