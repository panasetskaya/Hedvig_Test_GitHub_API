package com.panasetskaia.hedvigtestgithubapi.di

import android.content.Context
import com.panasetskaia.hedvigtestgithubapi.data.TextResourceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ResourceModule {
    @Provides
    fun provideTextResourceManager(@ApplicationContext appContext: Context): TextResourceManager {
        return TextResourceManager(appContext)
    }
}