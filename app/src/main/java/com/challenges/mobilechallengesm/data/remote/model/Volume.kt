package com.challenges.mobilechallengesm.data.remote.model

import com.google.gson.annotations.SerializedName

data class Volume(

    @SerializedName("value") val value: Int,
    @SerializedName("unit") val unit: String
)