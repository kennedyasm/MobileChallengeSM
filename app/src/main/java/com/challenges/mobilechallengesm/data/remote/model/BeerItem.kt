package com.challenges.mobilechallengesm.data.remote.model

import com.challenges.mobilechallengesm.data.local.entities.BeerEntity
import com.google.gson.annotations.SerializedName


data class BeerItem(

    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("tagline") val tagline: String = "",
    @SerializedName("first_brewed") val first_brewed: String = "",
    @SerializedName("description") val description: String,
    @SerializedName("image_url") val image_url: String?,
    @SerializedName("abv") val abv: Double? = null,
    @SerializedName("ibu") val ibu: Double? = null,
    @SerializedName("target_fg") val target_fg: Int? = null,
    @SerializedName("target_og") val target_og: Double? = null,
    @SerializedName("ebc") val ebc: Double? = null,
    @SerializedName("srm") val srm: Double? = null,
    @SerializedName("ph") val ph: Double? = null,
    @SerializedName("attenuation_level") val attenuation_level: Double? = null,
    @SerializedName("volume") val volume: Volume? = null,
    @SerializedName("boil_volume") val boil_volume: BoilVolume? = null,
    @SerializedName("method") val method: Method? = null,
    @SerializedName("ingredients") val ingredients: Ingredients? = null,
    @SerializedName("food_pairing") val food_pairing: List<String>? = null,
    @SerializedName("brewers_tips") val brewers_tips: String? = null,
    @SerializedName("contributed_by") val contributed_by: String? = null
) {
    internal fun toBeerEntity(): BeerEntity = BeerEntity(id, name, image_url ?: "", description)
}