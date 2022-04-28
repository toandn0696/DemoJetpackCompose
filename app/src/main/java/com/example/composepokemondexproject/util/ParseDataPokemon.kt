package com.example.composepokemondexproject.util

import androidx.compose.ui.graphics.Color
import com.example.composepokemondexproject.common.theme.*
import com.example.composepokemondexproject.model.PokemonStats
import com.example.composepokemondexproject.model.PokemonType

/**
 * Create by Nguyen Thanh Toan on 11/10/21
 *
 */
fun parseTypeToColor(type: PokemonType): Color =
    when (type.name.lowercase()) {
        "normal" -> TypeNormal
        "fire" -> TypeFire
        "water" -> TypeWater
        "electric" -> TypeElectric
        "grass" -> TypeGrass
        "ice" -> TypeIce
        "fighting" -> TypeFighting
        "poison" -> TypePoison
        "ground" -> TypeGround
        "flying" -> TypeFlying
        "psychic" -> TypePsychic
        "bug" -> TypeBug
        "rock" -> TypeRock
        "ghost" -> TypeGhost
        "dragon" -> TypeDragon
        "dark" -> TypeDark
        "steel" -> TypeSteel
        "fairy" -> TypeFairy
        else -> Color.Black
    }

fun parseStatToColor(statName: PokemonStats): Color = when (statName.name.lowercase()) {
    "hp" -> ColorApp
    "attack" -> ColorAtk
    "defense" -> ColorDef
    "special-attack" -> ColorSpAtk
    "special-defense" -> ColorSpDef
    "speed" -> ColorSpeed
    "exp" -> ColorExp
    else -> Color.White
}
fun parseStatToAbbr(statName: PokemonStats): String =
     when(statName.name.lowercase()) {
        "hp" -> "HP"
        "attack" -> "ATK"
        "defense" -> "DEF"
        "special-attack" -> "SP_ATK"
        "special-defense" -> "SP_DEF"
        "speed" -> "SPD"
        else -> "EXP"
    }
