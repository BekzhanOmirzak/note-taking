package com.android.notetaking.presentation.di.application

import androidx.lifecycle.ViewModel
import com.android.mydaggerproject.di.factory.ViewModelKey
import com.android.notetaking.presentation.ui.details.NoteDetailsFragment
import com.android.notetaking.presentation.ui.details.NoteDetailsViewModel
import com.android.notetaking.presentation.ui.home.HomeFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by bekjan on 29.03.2022.
 * email: bekjan.omirzak98@gmail.com
 */

@Module
abstract class ViewModelsModule {


    @IntoMap
    @Binds
    @ViewModelKey(HomeFragmentViewModel::class)
    abstract fun homeFragmentViewModel(homeFragmentViewModel: HomeFragmentViewModel): ViewModel

    @IntoMap
    @Binds
    @ViewModelKey(NoteDetailsViewModel::class)
    abstract fun noteDetailsViewModel(noteDetailsViewModel: NoteDetailsViewModel): ViewModel



}