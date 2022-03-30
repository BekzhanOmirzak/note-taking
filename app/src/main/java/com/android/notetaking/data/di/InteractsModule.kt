package com.android.notetaking.data.di

import com.android.notetaking.data.interactors.NoteInteractImpl
import com.android.notetaking.domain.interactors.NoteInteract
import dagger.Binds
import dagger.BindsInstance
import dagger.Module

/**
 * Created by bekjan on 30.03.2022.
 * email: bekjan.omirzak98@gmail.com
 */

@Module
abstract class InteractsModule {


    @Binds
    abstract fun bindsNoteInteractImpl(noteInteractImpl: NoteInteractImpl): NoteInteract


}