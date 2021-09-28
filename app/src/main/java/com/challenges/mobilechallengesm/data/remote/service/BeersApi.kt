package com.challenges.mobilechallengesm.data.remote.service

import com.challenges.mobilechallengesm.data.remote.model.BeerItem
import retrofit2.http.GET
import retrofit2.http.Query

interface BeersApi {

    @GET("beers")
    suspend fun getBeers(
        @Query("page") page: String = "2",
        @Query("per_page") per_page: String = "5"
    ): List<BeerItem>

}