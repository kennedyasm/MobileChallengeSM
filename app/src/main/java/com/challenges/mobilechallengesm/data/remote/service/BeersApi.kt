package com.challenges.mobilechallengesm.data.remote.service

import com.challenges.mobilechallengesm.data.remote.model.BeerItem
import retrofit2.http.GET
import retrofit2.http.Query

interface BeersApi {

    companion object {
        const val BEERS_PER_PAGE = "10"
    }

    @GET("beers")
    suspend fun getBeers(
        @Query("page") page: String,
        @Query("per_page") per_page: String = BEERS_PER_PAGE
    ): List<BeerItem>

    @GET("beers")
    suspend fun getBeersByQuery(
        @Query("beer_name") beer_name: String
    ): List<BeerItem>

}