package com.example.composepokemondexproject.data.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Create by Nguyen Thanh Toan on 11/3/21
 *
 */
@Serializable
data class BaseResponse(
    @SerialName("message") val message: String? = ""
)
