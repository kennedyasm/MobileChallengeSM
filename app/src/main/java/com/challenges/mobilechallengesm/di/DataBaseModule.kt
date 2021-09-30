package com.challenges.mobilechallengesm.di

import android.content.Context
import androidx.room.Room
import com.challenges.mobilechallengesm.data.local.BeersDataBase
import com.challenges.mobilechallengesm.data.local.dao.BeersDao
import com.challenges.mobilechallengesm.data.local.dao.RemoteKeyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    private const val DATA_BASE_NAME = "beers_data_base"

    @Singleton
    @Provides
    fun provideBeersDataBase(@ApplicationContext context: Context): BeersDataBase =
        Room.databaseBuilder(context, BeersDataBase::class.java, DATA_BASE_NAME).build()

    @Provides
    fun provideBeerDao(beersDataBase: BeersDataBase): BeersDao = beersDataBase.beersDao()

    @Provides
    fun provideRemoteKeyDao(beersDataBase: BeersDataBase): RemoteKeyDao =
        beersDataBase.remoteKeyDao()

}