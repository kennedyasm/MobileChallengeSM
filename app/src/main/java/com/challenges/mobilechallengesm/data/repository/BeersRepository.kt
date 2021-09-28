package com.challenges.mobilechallengesm.data.repository

import com.challenges.mobilechallengesm.data.remote.model.BeerItem
import com.challenges.mobilechallengesm.data.remote.model.BeersResponse
import kotlinx.coroutines.flow.Flow

interface BeersRepository {
    suspend fun getBeers(): Flow<List<BeerItem>>
}