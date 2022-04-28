package com.example.composepokemondexproject.data.network

import com.example.composepokemondexproject.data.network.response.PokemonResponse
import com.example.composepokemondexproject.data.network.response.PokemonDetailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Create by Nguyen Thanh Toan on 10/6/21
 *
 */
interface ApiService {
    @GET("pokemon")
    suspend fun getPokemon(
        @Query("limit") sizeList: Int,
        @Query("offset") page: Int
    ): Response<PokemonResponse>

    @GET("pokemon/{name}")
    suspend fun getDetailPokemon(@Path("name") name: String): Response<PokemonDetailResponse>
}
