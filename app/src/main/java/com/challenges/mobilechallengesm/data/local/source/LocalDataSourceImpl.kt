package com.challenges.mobilechallengesm.data.local.source

import androidx.paging.PagingSource
import com.challenges.mobilechallengesm.data.local.dao.BeersDao
import com.challenges.mobilechallengesm.data.local.dao.RemoteKeyDao
import com.challenges.mobilechallengesm.data.local.entities.BeerEntity
import com.challenges.mobilechallengesm.data.local.entities.RemoteKeyEntity
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val remoteKeyDao: RemoteKeyDao,
    private val beersDao: BeersDao
) : LocalDataSource {

    override suspend fun insertOrReplaceRemoteKey(remoteKey: RemoteKeyEntity) =
        remoteKeyDao.insertOrReplace(remoteKey)

    override suspend fun clearAllRemoteKeys() = remoteKeyDao.clearAll()

    override suspend fun getRemoteKey(): List<RemoteKeyEntity> = remoteKeyDao.getRemoteKey()

    override suspend fun clearAllBeers() = beersDao.clearAll()

    override suspend fun insertAllBeers(beers: List<BeerEntity>) = beersDao.insertAll(beers)

    override fun pagingSource(): PagingSource<Int, BeerEntity> = beersDao.pagingSource()
}