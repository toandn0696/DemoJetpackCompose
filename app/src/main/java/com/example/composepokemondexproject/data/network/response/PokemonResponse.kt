package com.example.composepokemondexproject.data.network.response

import com.example.composepokemondexproject.model.Pokemon
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Create by Nguyen Thanh Toan on 11/2/21
 *
 */
@Serializable
data class PokemonResponse(
    @SerialName("count") var count: Int?,
    @SerialName("next") var next: String?,
    @SerialName("previous") var previous: String?,
    @SerialName("results") var results: MutableList<Pokemon>?
)
