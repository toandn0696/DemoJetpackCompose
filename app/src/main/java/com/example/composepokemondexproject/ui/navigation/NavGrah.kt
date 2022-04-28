package com.example.composepokemondexproject.ui.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.HiltViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import com.example.composepokemondexproject.common.Arguments
import com.example.composepokemondexproject.ui.detail.DetailScreen
import com.example.composepokemondexproject.ui.detail.DetailViewModel
import com.example.composepokemondexproject.ui.favorite.FavoriteScreen
import com.example.composepokemondexproject.ui.favorite.FavoriteViewModel
import com.example.composepokemondexproject.ui.home.HomeScreen
import com.example.composepokemondexproject.ui.home.HomeViewModel

/**
 * Create by Nguyen Thanh Toan on 10/5/21
 *
 */

@ExperimentalCoilApi
@ExperimentalFoundationApi
@Composable
fun NavGraph(
    startDestination: String = ScreenRoute.HomeScreen.route,
    block: (Int) -> Unit = {}
) {
    val navController = rememberNavController()
    val action = remember(navController) { MainAction(navController) }

    NavHost(navController = navController, startDestination = startDestination) {

        //Home screen
        composable(ScreenRoute.HomeScreen.route) { navBackStackEntry ->
            val viewModelFactory = HiltViewModelFactory(LocalContext.current, navBackStackEntry)
            val viewModel = viewModel<HomeViewModel>(
                key = ScreenRoute.HomeScreen.route,
                factory = viewModelFactory
            )
            HomeScreen(viewModel, navController)
        }
        //Detail Screen
        composable("${ScreenRoute.DetailScreen.route}/{${Arguments.POKEMON_NAME}}/{${Arguments.POKEMON_COLOR}}",
            arguments = listOf(
                navArgument(Arguments.POKEMON_NAME) {
                    type = NavType.StringType
                },
                navArgument(Arguments.POKEMON_COLOR) {
                    type = NavType.IntType
                }
            )) { navBackStackEntry ->
            val viewModelFactory = HiltViewModelFactory(LocalContext.current, navBackStackEntry)
            val viewModel = viewModel<DetailViewModel>(
                key = ScreenRoute.DetailScreen.route,
                factory = viewModelFactory
            )
            val dominantColor = remember {
                val color = navBackStackEntry.arguments?.getInt(Arguments.POKEMON_COLOR)
                color?.let { Color(it) } ?: Color.White
            }
            val pokemonName = remember {
                navBackStackEntry.arguments?.getString(Arguments.POKEMON_NAME)
            }
            DetailScreen(
                viewModel = viewModel,
                navController = navController,
                name = pokemonName?.lowercase() ?: "",
                color = dominantColor
            )
        }
        //Favorite screen
        composable(ScreenRoute.FavoriteScreen.route) { navBackStackEntry ->
            val viewModelFactory = HiltViewModelFactory(LocalContext.current, navBackStackEntry)
            val viewModel = viewModel<FavoriteViewModel>(
                key = ScreenRoute.FavoriteScreen.route,
                factory = viewModelFactory
            )
            FavoriteScreen(viewModel, navController)
        }
    }
}

class MainAction(navController: NavController) {
    val upPress: () -> Unit = {
        navController.navigateUp()
    }
}