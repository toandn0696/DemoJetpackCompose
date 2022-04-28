package com.example.composepokemondexproject.common.extension

import retrofit2.Response

/**
 * Create by Nguyen Thanh Toan on 11/3/21
 *
 */
inline fun <T> apiCall(
    block: () -> Response<T>
): T {
    val response = block()
    val body = response.body()
    return when (response.isSuccessful && body != null) {
        true -> body
        false -> throw response.toError()
    }
}
