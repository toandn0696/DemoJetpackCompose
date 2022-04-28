package com.example.composepokemondexproject.ui.detail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.composepokemondexproject.common.extension.onError
import com.example.composepokemondexproject.common.extension.onSuccess
import com.example.composepokemondexproject.data.source.repository.PokemonRepository
import com.example.composepokemondexproject.data.source.repository.RoomRepository
import com.example.composepokemondexproject.model.PokemonDetailInfo
import com.example.composepokemondexproject.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

/**
 * Create by Nguyen Thanh Toan on 11/4/21
 *
 */
@HiltViewModel
class DetailViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository,
    private val roomRepo: RoomRepository
) : BaseViewModel() {
    private var pokemonDetail = mutableStateOf(PokemonDetailInfo())
    private var loadError = mutableStateOf("")
    private var loadingState = mutableStateOf(false)

    internal fun getDetailPokemon(name: String) {
        pokemonRepository.getPokemonInfo(name)
            .onStart {
                loadingState.value = true
            }
            .onCompletion {
                loadingState.value = false
            }
            .onSuccess {
                pokemonDetail.value = it
                roomRepo.isFavorite(pokemonDetail.value.id)
                    .onSuccess { isFavorite ->
                        pokemonDetail.value.isFavorite = isFavorite
                        println("TTTTTTTTT ${pokemonDetail.value.isFavorite}")
                    }
                    .launchIn(viewModelScope)
            }
            .onError {
                loadError.value = it.message ?: ""
            }
            .launchIn(viewModelScope)
    }

    internal fun getPokemonDetail() = pokemonDetail

    internal fun getError() = loadError

    internal fun isLoading() = loadingState

    internal fun isFavorite() = pokemonDetail.value.isFavorite

    internal fun addFavorite() {
        roomRepo.addFavoritePokemon(pokemonDetail.value)
            .onSuccess {
                pokemonDetail.value.isFavorite = true
            }
            .launchIn(viewModelScope)
    }

    internal fun deleteFavorite() {
        roomRepo.deleteFavoritePokemon(pokemonDetail.value)
            .onSuccess {
                pokemonDetail.value.isFavorite = false
            }
            .launchIn(viewModelScope)
    }
}
