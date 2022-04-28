package com.example.composepokemondexproject.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.composepokemondexproject.model.PokemonDetailInfo

/**
 * Create by Nguyen Thanh Toan on 11/11/21
 *
 */
@Dao
interface PokemonDAO {
    @Query("SELECT * FROM pokemon")
    suspend fun getPokemonFavorite(): List<PokemonDetailInfo>?

    @Insert
    suspend fun addFavoritePokemon(pokemon: PokemonDetailInfo)

    @Delete
    suspend fun deleteFavoritePokemon(pokemon: PokemonDetailInfo)

    @Query("SELECT is_favorite FROM pokemon WHERE id LIKE :id")
    suspend fun isFavorite(id: String): Boolean?
}
