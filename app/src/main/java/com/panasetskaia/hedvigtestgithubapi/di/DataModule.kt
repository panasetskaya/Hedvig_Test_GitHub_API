package com.panasetskaia.hedvigtestgithubapi.di

import com.panasetskaia.hedvigtestgithubapi.data.MainRepositoryImpl
import com.panasetskaia.hedvigtestgithubapi.domain.MainRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    abstract fun bindRepo(repoImpl: MainRepositoryImpl): MainRepository
}