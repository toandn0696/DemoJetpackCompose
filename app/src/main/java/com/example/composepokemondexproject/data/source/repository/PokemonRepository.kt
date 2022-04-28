package com.example.composepokemondexproject.data.source.repository

import com.example.composepokemondexproject.common.extension.FlowResult
import com.example.composepokemondexproject.common.extension.safeFlow
import com.example.composepokemondexproject.common.extension.toPokemonDetail
import com.example.composepokemondexproject.data.Repository
import com.example.composepokemondexproject.data.network.response.PokemonResponse
import com.example.composepokemondexproject.data.source.PokemonRemoteDatasource
import com.example.composepokemondexproject.model.PokemonDetailInfo
import com.example.composepokemondexproject.model.PokemonStats
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Create by Nguyen Thanh Toan on 11/3/21
 *
 */
class PokemonRepository @Inject constructor(
    private val remoteDatasource: PokemonRemoteDatasource
) : Repository() {
    internal fun getListPokemon(sizeList: Int, page: Int): Flow<FlowResult<PokemonResponse>> =
        safeFlow {
            remoteDatasource.getListPokemon(sizeList, page)
        }

    internal fun getPokemonInfo(name: String): Flow<FlowResult<PokemonDetailInfo>> = safeFlow {
        val result = remoteDatasource.getPokemonInfo(name)
        val pokemonDetail = result.toPokemonDetail(name)
        pokemonDetail.stats.add(PokemonStats("exp", result.base_experience.toString()))
        pokemonDetail
    }
}
