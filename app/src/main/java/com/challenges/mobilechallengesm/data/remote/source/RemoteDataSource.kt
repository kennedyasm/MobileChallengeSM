package com.challenges.mobilechallengesm.data.remote.source

import com.challenges.mobilechallengesm.data.remote.model.BeerItem

interface RemoteDataSource {
    suspend fun getBeers(page: String): List<BeerItem>
    suspend fun getBeersByQuery(query: String): List<BeerItem>
}