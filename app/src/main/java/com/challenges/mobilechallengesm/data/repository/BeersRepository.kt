package com.challenges.mobilechallengesm.data.repository

import com.challenges.mobilechallengesm.dto.BeerDto
import kotlinx.coroutines.flow.Flow

interface BeersRepository {
    suspend fun getBeers(): Flow<List<BeerDto>>
}