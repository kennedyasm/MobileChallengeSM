package com.challenges.mobilechallengesm.data.remote.source

import com.challenges.mobilechallengesm.data.remote.model.BeerItem
import com.challenges.mobilechallengesm.data.remote.service.BeersApi
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val api: BeersApi) : RemoteDataSource {
    override suspend fun getBeers(): List<BeerItem> = api.getBeers()
}