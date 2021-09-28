package com.challenges.mobilechallengesm.data.remote.source

import com.challenges.mobilechallengesm.data.remote.model.BeerItem

interface RemoteDataSource {
    suspend fun getBeers(): List<BeerItem>
}