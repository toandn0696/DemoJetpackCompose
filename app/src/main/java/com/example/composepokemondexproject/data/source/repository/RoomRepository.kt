package com.example.composepokemondexproject.data.source.repository

import com.example.composepokemondexproject.common.extension.FlowResult
import com.example.composepokemondexproject.common.extension.safeFlow
import com.example.composepokemondexproject.data.database.dao.PokemonDAO
import com.example.composepokemondexproject.data.source.DataSource.RoomRemoteDataSource
import com.example.composepokemondexproject.model.PokemonDetailInfo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Create by Nguyen Thanh Toan on 11/11/21
 *
 */
class RoomRepository @Inject constructor(private val pokemonDao: PokemonDAO): RoomRemoteDataSource {

    override fun getPokemonFavorite(): Flow<FlowResult<List<PokemonDetailInfo>>> =
        safeFlow {
            pokemonDao.getPokemonFavorite() ?: listOf()
        }

    override fun addFavoritePokemon(pokemon: PokemonDetailInfo): Flow<FlowResult<Unit>> =
        safeFlow {
            pokemon.isFavorite = true
            pokemonDao.addFavoritePokemon(pokemon)
        }

    override fun deleteFavoritePokemon(pokemon: PokemonDetailInfo): Flow<FlowResult<Unit>> =
        safeFlow {
            pokemon.isFavorite = false
            pokemonDao.deleteFavoritePokemon(pokemon)
        }
    override fun isFavorite(id: String): Flow<FlowResult<Boolean>> =
        safeFlow {
            pokemonDao.isFavorite(id) ?: false
        }
}
