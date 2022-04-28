package com.example.composepokemondexproject.model.converter

import androidx.room.TypeConverter
import com.example.composepokemondexproject.model.PokemonStats
import com.example.composepokemondexproject.model.PokemonType
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Create by Nguyen Thanh Toan on 11/11/21
 *
 */
class PokemonConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromStringToStatPokemon(json: String): List<PokemonStats> {
        val pokemonStats = object : TypeToken<List<PokemonStats>>() {}.type
        return gson.fromJson(json, pokemonStats)
    }

    @TypeConverter
    fun fromStatPokemonToString(data: List<PokemonStats>): String {
        return gson.toJson(data)
    }

    @TypeConverter
    fun fromStringToTypePokemon(json: String): List<PokemonType> {
        val pokemonTypes = object : TypeToken<List<PokemonType>>() {}.type
        return gson.fromJson(json, pokemonTypes)
    }

    @TypeConverter
    fun fromTypePokemonToString(data: List<PokemonType>): String {
        return gson.toJson(data)
    }
}
