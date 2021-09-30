package com.challenges.mobilechallengesm.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.challenges.mobilechallengesm.data.local.dao.BeersDao
import com.challenges.mobilechallengesm.data.local.dao.RemoteKeyDao
import com.challenges.mobilechallengesm.data.local.entities.BeerEntity
import com.challenges.mobilechallengesm.data.mediator.PageKeyRemoteMediator
import com.challenges.mobilechallengesm.data.remote.source.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BeersRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val beersDao: BeersDao,
    private val remoteKeyDao: RemoteKeyDao
) : BeersRepository {


    @ExperimentalPagingApi
    override fun getBeers(): Flow<PagingData<BeerEntity>> = Pager(
        config = PagingConfig(pageSize = 10),
        remoteMediator = PageKeyRemoteMediator(
            remoteDataSource,
            beersDao,
            remoteKeyDao
        )
    ) { beersDao.pagingSource() }.flow


}