package com.cheesejuice.fancymansionsample.nav

import androidx.navigation.NavType
import androidx.navigation.navArgument

interface NavDestination {
    val route: String
}

object ReadStart : NavDestination {
    override val route = "read.start"
    const val readBookIdArg = "read_book_id"
    val routeWithArgs = "$route/{$readBookIdArg}"
    val arguments = listOf(
        navArgument(readBookIdArg) { type = NavType.StringType }
    )
}

object ReadSlide : NavDestination {
    override val route = "read.slide"
    const val readBookIdArg = "read_book_id"
    const val readSlideIdArg = "read_slide_id"
    val routeWithArgs = "$route/{$readSlideIdArg}"
    val arguments = listOf(
        navArgument(readSlideIdArg) { type = NavType.StringType }
    )
}