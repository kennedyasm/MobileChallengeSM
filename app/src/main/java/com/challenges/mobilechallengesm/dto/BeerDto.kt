package com.challenges.mobilechallengesm.dto

import com.challenges.mobilechallengesm.data.local.entities.BeerEntity
import com.challenges.mobilechallengesm.data.remote.model.BeerItem

data class BeerDto(
    val id: Int,
    val name: String,
    val image_url: String,
    val description: String
)

fun BeerEntity.toBeerDto(): BeerDto = BeerDto(id, name, image_url, description)
fun BeerItem.toBeerDto(): BeerDto = BeerDto(id, name, image_url ?: "", description)