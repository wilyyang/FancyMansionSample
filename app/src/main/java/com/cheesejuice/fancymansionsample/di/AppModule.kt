package com.cheesejuice.fancymansionsample.di

import android.content.Context
import com.cheesejuice.fancymansionsample.data.repositories.PreferenceProvider
import com.cheesejuice.fancymansionsample.data.repositories.file.FileRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule{

    @Singleton
    @Provides
    fun provideFileRepository(@ApplicationContext context: Context): FileRepository {
        return FileRepository(context)
    }

    @Singleton
    @Provides
    fun providePreferenceProvider(@ApplicationContext context: Context): PreferenceProvider {
        return PreferenceProvider(context)
    }
}