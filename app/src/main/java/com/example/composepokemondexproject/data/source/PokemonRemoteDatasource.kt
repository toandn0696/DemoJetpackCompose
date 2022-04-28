package com.example.composepokemondexproject.data.source

import com.example.composepokemondexproject.common.extension.apiCall
import com.example.composepokemondexproject.data.network.ApiService
import com.example.composepokemondexproject.data.network.response.PokemonResponse
import com.example.composepokemondexproject.data.network.response.PokemonDetailResponse
import javax.inject.Inject

/**
 * Create by Nguyen Thanh Toan on 11/3/21
 *
 */
class PokemonRemoteDatasource @Inject constructor(
    private val api: ApiService
) {
    suspend fun getListPokemon(sizeList: Int, page: Int): PokemonResponse = apiCall {
        api.getPokemon(sizeList, page)
    }

    suspend fun getPokemonInfo(name:String): PokemonDetailResponse = apiCall {
        api.getDetailPokemon(name)
    }
}
