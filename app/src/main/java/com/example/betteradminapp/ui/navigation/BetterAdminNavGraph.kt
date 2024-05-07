package com.example.betteradminapp.ui.navigation

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.betteradminapp.ui.home.HomeDestination
import com.example.betteradminapp.ui.home.HomeScreen
import com.example.betteradminapp.ui.screens.MainDestination
import com.example.betteradminapp.ui.screens.MainScreen

// Provides Navigation graph for the application.
@Composable
fun BetterAdminNavHost(
    windowSize: WindowWidthSizeClass,
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen(
                windowSize = windowSize,
                onDonePressed = { navController.navigate(MainDestination.route) }
            )
        }
        composable(route = MainDestination.route) {
            MainScreen(
                windowSize = windowSize,
                navigateUp = { navController.navigateUp() }
            )
        }
    }
}