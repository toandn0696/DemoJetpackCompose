package com.example.composepokemondexproject.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.composepokemondexproject.data.database.dao.PokemonDAO
import com.example.composepokemondexproject.model.PokemonDetailInfo

/**
 * Create by Nguyen Thanh Toan on 11/11/21
 *
 */
@Database(entities = [PokemonDetailInfo::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pokemonDAO(): PokemonDAO

    companion object{
        const val DATABASE_NAME = "pokemon_db"
    }
}
