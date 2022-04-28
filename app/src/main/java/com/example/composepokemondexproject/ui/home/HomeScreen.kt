package com.example.composepokemondexproject.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
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
import com.example.composepokemondexproject.model.Pokemon
import com.example.composepokemondexproject.ui.navigation.ScreenRoute
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch

/**
 * Create by Nguyen Thanh Toan on 10/5/21
 *
 */
@ExperimentalFoundationApi
@Composable
fun HomeScreen(viewModel: HomeViewModel, navController: NavController) {
    val systemController = rememberSystemUiController()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name), color = Color.White)
                },
                actions = {
                    IconButton(onClick = {
                        navController.navigate(ScreenRoute.FavoriteScreen.route)
                    }) {
                        Icon(
                            Icons.Filled.Favorite,
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
        ContentHome(navController = navController, viewModel = viewModel)
    }
}

@ExperimentalCoilApi
@ExperimentalFoundationApi
@Composable
private fun ContentHome(navController: NavController, viewModel: HomeViewModel) {
    val pokemonList by remember { viewModel.pokemonList() }
    val endReached by remember { viewModel.endReached() }
    val loadError by remember { viewModel.loadError() }
    val isLoading by remember { viewModel.loadingState() }

    Box(
        modifier = Modifier
            .background(BackGroundApp)
            .fillMaxWidth()
            .fillMaxHeight(),
        contentAlignment = Alignment.Center
    ) {
        when {
            isLoading -> HomeLoadingScreen()
            loadError.isNotEmpty() -> {
                val (_, setShowDialog) = remember {
                    mutableStateOf(false)
                }
                AlertDialog(onDismissRequest = {
                },
                    title = {
                        Text(loadError)
                    },
                    confirmButton = {
                        Button(onClick = { setShowDialog(false) }) {
                            Text(text = "Ok")
                        }
                    })
            }
            else -> {
                LazyVerticalGrid(cells = GridCells.Fixed(2),
                    contentPadding = PaddingValues(10.dp)) {
                    items(pokemonList.size) { index ->
                        PokemonItem(
                            data = pokemonList[index],
                            viewModel = viewModel,
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HomeLoadingScreen() {
    Column {
        CircularProgressIndicator(
            modifier = Modifier
                .width(32.dp)
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = stringResource(R.string.home_screen_text_view_text_loading),
            textAlign = TextAlign.Center
        )
    }
}

@ExperimentalCoilApi
@Composable
private fun PokemonItem(
    data: Pokemon, viewModel: HomeViewModel,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val defaultColor = MaterialTheme.colors.surface
    var colorState by remember {
        mutableStateOf(defaultColor)
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .padding(5.dp)
            .shadow(5.dp, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .aspectRatio(1f)
            .background(colorState)
            .clickable {
                val navRoute =
                    "${ScreenRoute.DetailScreen.route}/${data.name}/${colorState.toArgb()}"
                navController.navigate(navRoute)
            }
    ) {
        Column {
            val painter = rememberImagePainter(data.imageUrl)
            val painterState = painter.state
            Image(
                painter = painter, contentDescription = data.name,
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
                    .align(Alignment.CenterHorizontally)
            )
            when (painterState) {
                is ImagePainter.State.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .scale(0.5f)
                            .align(Alignment.CenterHorizontally)
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
            Text(
                text = data.name ?: "",
                fontSize = 20.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = Color.White
            )
        }
    }
}
