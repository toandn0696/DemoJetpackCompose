package com.example.composepokemondexproject.data.error

/**
 * Create by Nguyen Thanh Toan on 11/3/21
 *
 */
data class RepositoryException(
    val code: Int,
    val errorBody: String?,
    val msg: String
) : RuntimeException(msg)
