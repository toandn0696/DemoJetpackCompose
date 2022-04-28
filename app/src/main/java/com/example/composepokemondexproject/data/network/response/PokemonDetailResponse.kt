package com.example.composepokemondexproject.data.network.response

import com.example.composepokemondexproject.model.Stat
import com.example.composepokemondexproject.model.Type
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonDetailResponse(
    @SerialName("base_experience") var base_experience: Int,
    var height: Int,
    var id: Int,
    var stats: List<Stat>,
    var types: List<Type>,
    var weight: Int
)