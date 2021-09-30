package com.challenges.mobilechallengesm.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.challenges.mobilechallengesm.data.local.BeersDataBase.Companion.DATA_BASE_VERSION
import com.challenges.mobilechallengesm.data.local.dao.BeersDao
import com.challenges.mobilechallengesm.data.local.dao.RemoteKeyDao
import com.challenges.mobilechallengesm.data.local.entities.BeerEntity
import com.challenges.mobilechallengesm.data.local.entities.RemoteKeyEntity


@Database(entities = [BeerEntity::class, RemoteKeyEntity::class], version = DATA_BASE_VERSION)
abstract class BeersDataBase : RoomDatabase() {

    companion object {
        const val DATA_BASE_VERSION = 1
    }

    abstract fun beersDao(): BeersDao
    abstract fun remoteKeyDao(): RemoteKeyDao

}