package com.example.composepokemondexproject.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.composepokemondexproject.model.converter.PokemonConverter

/**
 * Create by Nguyen Thanh Toan on 11/9/21
 *
 */

@Entity(tableName = "pokemon")
@TypeConverters(PokemonConverter::class)
data class PokemonDetailInfo(
    val name: String = "",
    @PrimaryKey
    val id: String = "",
    @ColumnInfo(name = "url_image") val ulrImage: String = "",
    var height: Int = 0,
    var stats: MutableList<PokemonStats> = mutableListOf(),
    var types: List<PokemonType> = listOf(),
    var weight: Int = 0,
    @ColumnInfo(name = "is_favorite") var isFavorite: Boolean = false
)

data class PokemonStats(val name: String, val baseStat: String)

data class PokemonType(val name: String)