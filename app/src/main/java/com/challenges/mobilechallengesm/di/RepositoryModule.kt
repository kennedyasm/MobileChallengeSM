package com.challenges.mobilechallengesm.di

import com.challenges.mobilechallengesm.data.repository.BeersRepository
import com.challenges.mobilechallengesm.data.repository.BeersRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindBeerRepository(impl: BeersRepositoryImpl): BeersRepository
}