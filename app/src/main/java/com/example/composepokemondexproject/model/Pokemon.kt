package com.example.composepokemondexproject.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Create by Nguyen Thanh Toan on 11/2/21
 *
 */
@Serializable
data class Pokemon(
    @SerialName("name") var name: String?,
    @SerialName("url") var url: String?
) {
    var imageUrl: String? = ""
}
