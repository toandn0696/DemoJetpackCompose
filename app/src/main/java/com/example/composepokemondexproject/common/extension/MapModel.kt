package com.example.composepokemondexproject.common.extension

import com.example.composepokemondexproject.data.network.response.PokemonDetailResponse
import com.example.composepokemondexproject.model.PokemonDetailInfo
import com.example.composepokemondexproject.model.PokemonStats
import com.example.composepokemondexproject.model.PokemonType

/**
 * Create by Nguyen Thanh Toan on 11/9/21
 *
 */
fun PokemonDetailResponse.toPokemonDetail(name: String): PokemonDetailInfo {
    val imgUrl =
        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"

    return PokemonDetailInfo(
        name,
        id.toString(),
        imgUrl,
        height,
        stats.map { PokemonStats(it.stat.name, it.baseStat.toString()) }.toMutableList(),
        types.map { PokemonType(it.type.name) },
        weight
    )
}