package com.cheesejuice.fancymansionsample.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cheesejuice.fancymansionsample.ui.contents.reader.slide.ReadSlideScreen
import com.cheesejuice.fancymansionsample.ui.contents.reader.start.ReadStartScreen

@Composable
fun FancyMansionNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = ReadStart.route,
        modifier = modifier
    ) {
        composable(route = ReadStart.route){
            ReadStartScreen(onClickReadBookStart = {
                navController.navigate(ReadSlide.route)
            })
        }
        composable(route = ReadSlide.route){
            ReadSlideScreen()
        }
    }
}