package com.challenges.mobilechallengesm.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.challenges.mobilechallengesm.data.local.entities.BeerEntity
import com.challenges.mobilechallengesm.data.local.source.LocalDataSource
import com.challenges.mobilechallengesm.data.mediator.PageKeyRemoteMediator
import com.challenges.mobilechallengesm.data.remote.source.RemoteDataSource
import com.challenges.mobilechallengesm.dto.BeerDto
import com.challenges.mobilechallengesm.dto.toBeerDto
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class BeersRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val dispatcher: CoroutineDispatcher
) : BeersRepository {


    @ExperimentalPagingApi
    override fun getBeers(): Flow<PagingData<BeerEntity>> = Pager(
        config = PagingConfig(pageSize = 10),
        remoteMediator = PageKeyRemoteMediator(
            remoteDataSource,
            localDataSource
        )
    ) { localDataSource.pagingSource() }.flow

    override fun getBeersByQuery(query: String): Flow<List<BeerDto>> = flow {
        val list: List<BeerDto> = remoteDataSource.getBeersByQuery(query).map { it.toBeerDto() }
        emit(list)
    }.flowOn(dispatcher)


}