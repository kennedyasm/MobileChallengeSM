package com.challenges.mobilechallengesm.data.local.source

import androidx.paging.PagingSource
import com.challenges.mobilechallengesm.data.local.entities.BeerEntity
import com.challenges.mobilechallengesm.data.local.entities.RemoteKeyEntity

interface LocalDataSource {

    suspend fun insertOrReplaceRemoteKey(remoteKey: RemoteKeyEntity)
    suspend fun clearAllRemoteKeys()
    suspend fun getRemoteKey(): List<RemoteKeyEntity>
    suspend fun clearAllBeers()
    suspend fun insertAllBeers(beers: List<BeerEntity>)
    fun pagingSource(): PagingSource<Int, BeerEntity>

}