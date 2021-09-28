package com.challenges.mobilechallengesm.data.remote.model

import com.google.gson.annotations.SerializedName


data class Ingredients(

    @SerializedName("malt") val malt: List<Malt>,
    @SerializedName("hops") val hops: List<Hops>,
    @SerializedName("yeast") val yeast: String
)