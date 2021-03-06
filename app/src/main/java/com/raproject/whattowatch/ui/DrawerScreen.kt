package com.raproject.whattowatch.ui

import com.raproject.whattowatch.R

sealed class DrawerScreen(val title: String, val iconId: Int) {

    object Movies : DrawerScreen("Movies", R.drawable.ic_movie)
    object Serials : DrawerScreen("Serials", R.drawable.ic_serials)
    object Cartoons : DrawerScreen("Cartoons", R.drawable.ic_cartoons)
    object Anime : DrawerScreen("Anime", R.drawable.ic_anime)
    object Top100 : DrawerScreen("Our Top 100", R.drawable.ic_top)
    object Favorite : DrawerScreen("Favorite", R.drawable.ic_favorite)

    companion object {
        val screens = listOf(
            Movies, Serials, Cartoons, Anime, Top100, Favorite
        )
    }
}
