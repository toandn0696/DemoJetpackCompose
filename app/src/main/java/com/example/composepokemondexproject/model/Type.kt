package com.example.composepokemondexproject.model

import kotlinx.serialization.Serializable

@Serializable
data class Type(
    var slot: Int,
    var type: TypeX
)