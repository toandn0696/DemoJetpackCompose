package com.example.composepokemondexproject.ui.favorite

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.example.composepokemondexproject.R
import com.example.composepokemondexproject.common.theme.BackGroundApp
import com.example.composepokemondexproject.common.theme.ColorApp
import com.example.composepokemondexproject.common.theme.ColorTypeFavorite
import com.example.composepokemondexproject.model.PokemonDetailInfo
import com.example.composepokemondexproject.model.PokemonType
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch

/**
 * Create by Nguyen Thanh Toan on 11/15/21
 *
 */

@ExperimentalCoilApi
@Composable
fun FavoriteScreen(viewModel: FavoriteViewModel, navController: NavController) {
    val systemController = rememberSystemUiController()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.title_favorite), color = Color.White)
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "Localized description",
                            tint = Color.White
                        )
                    }
                },
                elevation = 2.dp,
                backgroundColor = ColorApp
            )
        }
    ) {
        systemController.setSystemBarsColor(ColorApp)
        FavoriteContent(viewModel)
    }
}

@ExperimentalCoilApi
@Composable
fun FavoriteContent(
    viewModel: FavoriteViewModel
) {
    Box(
        modifier = Modifier
            .background(BackGroundApp)
            .fillMaxWidth()
            .fillMaxHeight(),
        contentAlignment = Alignment.TopCenter
    ) {
        LazyColumn(contentPadding = PaddingValues(20.dp)) {
            items(viewModel.getPokemonList().size) {
                ItemFavorite(data = viewModel.getPokemonList()[it], viewModel = viewModel)
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

@ExperimentalCoilApi
@Composable
fun ItemFavorite(
    data: PokemonDetailInfo, viewModel: FavoriteViewModel,
    modifier: Modifier = Modifier
) {
    val defaultColor = MaterialTheme.colors.surface
    var colorState by remember {
        mutableStateOf(defaultColor)
    }
    val painter = rememberImagePainter(data.ulrImage)
    val painterState = painter.state
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .shadow(5.dp, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .height(150.dp)
            .fillMaxWidth()
            .aspectRatio(1f)
            .background(colorState)
    ) {
        Row() {
            when (painterState) {
                is ImagePainter.State.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .scale(0.5f)
                            .align(Alignment.CenterVertically)
                    )
                }
                is ImagePainter.State.Success -> {
                    LaunchedEffect(painter) {
                        launch {
                            val drawable = painter.imageLoader.execute(painter.request).drawable
                            viewModel.getColorFromDrawable(drawable) { color ->
                                colorState = color
                            }
                        }
                    }
                }
                else -> {
                    //do nothing
                }
            }
            Box(
                modifier = Modifier
                    .weight(2f)
                    .align(Alignment.CenterVertically)
            ) {
                Column {
                    Text(
                        text = data.name.replaceFirstChar { firstChar -> firstChar.uppercase() },
                        fontSize = 20.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    ListPokemonType(types = data.types)
                }
            }
            Box(
                modifier = Modifier
                    .weight(1.5f)
                    .padding(5.dp)
                    .align(Alignment.CenterVertically)
            ) {
                Image(
                    painter = painter, contentDescription = data.name,
                    modifier = Modifier
                        .width(150.dp)
                        .height(150.dp)
                        .padding(end = 10.dp)
                        .align(Alignment.CenterEnd)
                )
            }
        }
    }
}

@Composable
fun ListPokemonType(types: List<PokemonType>) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(16.dp)
    ) {
        types.forEach {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
                    .clip(CircleShape)
                    .background(ColorTypeFavorite)
                    .height(25.dp)
            ) {
                Text(
                    text = it.name.replaceFirstChar { firstChar -> firstChar.uppercase() },
                    fontSize = 10.sp,
                    color = Color.White
                )
            }
        }
    }
}
