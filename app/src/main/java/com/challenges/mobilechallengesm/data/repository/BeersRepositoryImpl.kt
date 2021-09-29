package com.challenges.mobilechallengesm.data.repository

import com.challenges.mobilechallengesm.data.remote.source.RemoteDataSource
import com.challenges.mobilechallengesm.dto.BeerDto
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class BeersRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val dispatcher: CoroutineDispatcher
) : BeersRepository {

    override suspend fun getBeers(): Flow<List<BeerDto>> = flow {
        emit(remoteDataSource.getBeers().map { it.toBeerDto() })
    }.flowOn(dispatcher)

}