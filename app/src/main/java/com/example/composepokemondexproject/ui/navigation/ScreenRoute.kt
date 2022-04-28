package com.example.composepokemondexproject.ui.navigation

/**
 * Create by Nguyen Thanh Toan on 10/5/21
 *
 */
sealed class ScreenRoute(val route: String) {
    object HomeScreen : ScreenRoute("homeScreen")
    object DetailScreen : ScreenRoute("detailScreen")
    object FavoriteScreen : ScreenRoute("favoriteScreen")
}
