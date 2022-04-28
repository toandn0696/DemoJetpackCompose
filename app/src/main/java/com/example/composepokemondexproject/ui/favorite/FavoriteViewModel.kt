package com.example.composepokemondexproject.ui.favorite

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.example.composepokemondexproject.common.extension.onSuccess
import com.example.composepokemondexproject.data.source.repository.RoomRepository
import com.example.composepokemondexproject.model.PokemonDetailInfo
import com.example.composepokemondexproject.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject

/**
 * Create by Nguyen Thanh Toan on 11/15/21
 *
 */
@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val roomRepo: RoomRepository
) : BaseViewModel() {
    private var pokemonList = mutableStateOf<MutableList<PokemonDetailInfo>>(mutableListOf())

    init {
        roomRepo.getPokemonFavorite()
            .onSuccess {
                pokemonList.value.addAll(it)
                println("TTTTTTTTT ${pokemonList.value}")
            }
            .launchIn(viewModelScope)
    }

    internal fun getColorFromDrawable(drawable: Drawable?, onFinish: (Color) -> Unit) {
        val bmp = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)
        Palette.from(bmp).generate {
            it?.dominantSwatch?.rgb?.let { color ->
                onFinish(Color(color))
            }
        }
    }

    internal fun getPokemonList()= pokemonList.value
}
