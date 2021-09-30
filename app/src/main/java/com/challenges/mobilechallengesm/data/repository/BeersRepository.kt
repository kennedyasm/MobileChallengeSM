package com.challenges.mobilechallengesm.data.repository

import androidx.paging.PagingData
import com.challenges.mobilechallengesm.data.local.entities.BeerEntity
import kotlinx.coroutines.flow.Flow

interface BeersRepository {
    fun getBeers(): Flow<PagingData<BeerEntity>>
}