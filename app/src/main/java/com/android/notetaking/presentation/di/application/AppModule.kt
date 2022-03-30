package com.android.notetaking.presentation.di.application

import android.app.Application
import androidx.room.Room
import com.android.notetaking.data.database.AppDatabase
import com.android.notetaking.presentation.ui.MainActivity
import dagger.Module
import dagger.Provides

/**
 * Created by bekjan on 29.03.2022.
 * email: bekjan.omirzak98@gmail.com
 */

@Module
class AppModule {


    @Provides
    @AppScope
    fun provideAppDataDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, AppDatabase.DB_NAME).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @AppScope
    fun provideNoteDao(appDatabase: AppDatabase) = appDatabase.noteDao


}