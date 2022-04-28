package com.example.composepokemondexproject.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Stat(
    @SerialName("base_stat")var baseStat: Int,
    var effort: Int,
    var stat: StatX
)