package com.example.composepokemondexproject.ui.detail

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.composepokemondexproject.R
import com.example.composepokemondexproject.common.theme.BackGroundApp
import com.example.composepokemondexproject.model.PokemonDetailInfo
import com.example.composepokemondexproject.model.PokemonType
import com.example.composepokemondexproject.util.parseStatToAbbr
import com.example.composepokemondexproject.util.parseStatToColor
import com.example.composepokemondexproject.util.parseTypeToColor
import com.google.accompanist.systemuicontroller.rememberSystemUiController

/**
 * Create by Nguyen Thanh Toan on 11/4/21
 *
 */

@ExperimentalCoilApi
@Composable
fun DetailScreen(
    viewModel: DetailViewModel,
    navController: NavController,
    name: String,
    color: Color
) {
    LaunchedEffect(key1 = true) {
        viewModel.getDetailPokemon(name)
    }
    val pokemonInfo by remember { viewModel.getPokemonDetail() }
    val loadError by remember { viewModel.getError() }
    val isLoading by remember { viewModel.isLoading() }
    val scrollState = rememberScrollState()
    val systemController = rememberSystemUiController()
    Box(
        modifier = Modifier
            .background(BackGroundApp)
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        when {
            isLoading -> {
                HomeLoadingScreen()
            }
            loadError.isNotEmpty() -> {

            }
            else -> {
                systemController.setSystemBarsColor(color)
                if (pokemonInfo.id.isNotEmpty()) {
                    Column(
                        Modifier.verticalScroll(scrollState)
                    ) {
                        HeaderDetail(
                            data = pokemonInfo,
                            navController = navController,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp))
                                .background(color),
                            viewModel
                        )
                        Spacer(modifier = Modifier.height(15.dp))
                        BodyDetail(data = pokemonInfo)
                    }
                }
            }
        }
    }
}


@Composable
fun HomeLoadingScreen() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
fun HeaderDetail(
    data: PokemonDetailInfo,
    navController: NavController, modifier: Modifier = Modifier,
    viewModel: DetailViewModel
) {
    val painter = rememberImagePainter(data.ulrImage)
    val iconState = remember {
        mutableStateOf(
            if (viewModel.isFavorite()) Icons.Filled.Favorite
            else Icons.Filled.FavoriteBorder
        )
    }
    Box(
        modifier = modifier
    ) {
        Column {
            Row(modifier = Modifier.fillMaxWidth()) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "detail", tint = Color.White)
                }
                Text(
                    text = stringResource(id = R.string.app_name),
                    Modifier.align(Alignment.CenterVertically),
                    color = Color.White
                )
                Box(Modifier.fillMaxWidth()) {
                    IconButton(
                        onClick = {
                            if (viewModel.isFavorite()) {
                                viewModel.deleteFavorite()
                                iconState.value = Icons.Filled.FavoriteBorder
                            } else {
                                viewModel.addFavorite()
                                iconState.value = Icons.Filled.Favorite
                            }
                        },
                        Modifier.align(Alignment.CenterEnd)
                    ) {
                        Icon(
                            iconState.value,
                            contentDescription = "favorite",
                            tint = Color.White
                        )
                    }
                }
            }
            Image(
                painter = painter, contentDescription = "",
                modifier = Modifier
                    .padding(top = 5.dp)
                    .width(150.dp)
                    .height(150.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
fun BodyDetail(data: PokemonDetailInfo) {
    val maxValue = remember {
        data.stats.maxOf { it.baseStat.toInt() }
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = data.name.replaceFirstChar { firstChar -> firstChar.uppercase() },
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            fontSize = 36.sp,
            color = Color.White
        )
        ListPokemonType(types = data.types)
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            ) {
                Text(
                    "${data.weight / 10f} KG",
                    fontSize = 20.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Weight",
                    color = Color.Gray,
                    fontSize = 15.sp
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            ) {
                Text(
                    "${data.height / 10f} M",
                    fontSize = 20.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    "Height",
                    color = Color.Gray,
                    fontSize = 15.sp
                )
            }
        }
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = "Base Stats",
            color = Color.White,
            fontSize = 21.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(10.dp))
        data.stats.forEach { stat ->

            InfoStats(
                title = parseStatToAbbr(stat),
                statValue = stat.baseStat.toInt(),
                color = parseStatToColor(stat),
                statMaxValue = maxValue
            )
            Spacer(modifier = Modifier.height(15.dp))
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
                    .background(parseTypeToColor(it))
                    .height(35.dp)
            ) {
                Text(
                    text = it.name.replaceFirstChar { firstChar -> firstChar.uppercase() },
                    fontSize = 20.sp,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun InfoStats(
    title: String,
    statValue: Int,
    color: Color,
    statMaxValue: Int,
    animDuration: Int = 1000,
    animDelay: Int = 0
) {
    var animationPlayed by remember {
        mutableStateOf(false)
    }
    val curProgress = animateFloatAsState(
        targetValue = if (animationPlayed) {
            statValue / statMaxValue.toFloat()
        } else {
            0f
        },
        animationSpec = tween(animDuration, animDelay)
    )
    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }
    Box(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .fillMaxWidth()
            .height(30.dp)
            .clip(CircleShape)
            .background(
                if (isSystemInDarkTheme()) {
                    Color.Black
                } else {
                    Color.LightGray
                }
            )
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(curProgress.value)
                .clip(CircleShape)
                .background(color)
        ) {
            Text(
                title,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 10.dp),
                color = Color.White
            )
            Text(
                text = (curProgress.value * statMaxValue).toInt().toString(),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(end = 10.dp),
                color = Color.White
            )
        }
    }
}
