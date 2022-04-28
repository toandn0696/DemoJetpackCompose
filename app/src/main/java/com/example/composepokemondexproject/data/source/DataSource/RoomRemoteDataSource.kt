package com.example.composepokemondexproject.data.source.DataSource

import com.example.composepokemondexproject.common.extension.FlowResult
import com.example.composepokemondexproject.model.PokemonDetailInfo
import kotlinx.coroutines.flow.Flow

/**
 * Create by Nguyen Thanh Toan on 11/11/21
 *
 */
interface RoomRemoteDataSource {
    fun getPokemonFavorite(): Flow<FlowResult<List<PokemonDetailInfo>>>

    fun addFavoritePokemon(pokemon: PokemonDetailInfo): Flow<FlowResult<Unit>>

    fun deleteFavoritePokemon(pokemon: PokemonDetailInfo): Flow<FlowResult<Unit>>

    fun isFavorite(id: String): Flow<FlowResult<Boolean>>
}
