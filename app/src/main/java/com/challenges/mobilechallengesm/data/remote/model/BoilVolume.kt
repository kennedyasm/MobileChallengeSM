package com.challenges.mobilechallengesm.data.remote.model

import com.google.gson.annotations.SerializedName

data class BoilVolume (

	@SerializedName("value") val value : Int,
	@SerializedName("unit") val unit : String
)