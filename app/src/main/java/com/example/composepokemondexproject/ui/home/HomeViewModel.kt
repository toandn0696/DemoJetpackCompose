package com.example.composepokemondexproject.ui.home

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.*
import androidx.palette.graphics.Palette
import com.example.composepokemondexproject.common.extension.onError
import com.example.composepokemondexproject.common.extension.onSuccess
import com.example.composepokemondexproject.data.source.repository.PokemonRepository
import com.example.composepokemondexproject.data.source.repository.RoomRepository
import com.example.composepokemondexproject.model.Pokemon
import com.example.composepokemondexproject.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

/**
 * Create by Nguyen Thanh Toan on 11/2/21
 *
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository,
) : BaseViewModel() {
    companion object {
        private const val PAGE_SIZE = 20
    }

    private var currentPage = 0

    private var pokemonList = mutableStateOf<MutableList<Pokemon>>(mutableListOf())
    private var loadError = mutableStateOf("")
    private var loadingState = mutableStateOf(false)
    private var endReached = mutableStateOf(false)

    init {
        getListPokemon()
    }

    internal fun getListPokemon() {
        pokemonRepository.getListPokemon(PAGE_SIZE, currentPage * PAGE_SIZE)
            .onStart {
                loadingState.value = true
            }
            .onCompletion {
                loadingState.value = false
            }
            .onSuccess { data ->
                endReached.value = currentPage * PAGE_SIZE >= data.count ?: 0
                data.results?.forEach { pokemon ->
                    var number = ""
                    pokemon.url?.let { url ->
                        number = if (url.endsWith("/")) {
                            url.dropLast(1).takeLastWhile { it.isDigit() }
                        } else {
                            url.takeLastWhile { it.isDigit() }
                        }
                    }
                    val url =
                        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$number.png"
                    pokemon.imageUrl = url
                    pokemonList.value.add(pokemon)
                }
                currentPage++
            }
            .onError {
                loadError.value = it.message ?: ""
            }.launchIn(viewModelScope)
    }

    internal fun getColorFromDrawable(drawable: Drawable?, onFinish: (Color) -> Unit) {
        val bmp = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)
        Palette.from(bmp).generate {
            it?.dominantSwatch?.rgb?.let { color ->
                onFinish(Color(color))
            }
        }
    }

    internal fun pokemonList() = pokemonList

    internal fun loadingState() = loadingState

    internal fun endReached() = endReached

    internal fun loadError() = loadError

}