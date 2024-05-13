package com.example.betteradminapp.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.betteradminapp.ui.home.HomeDestination
import com.example.betteradminapp.ui.home.HomeScreen
import com.example.betteradminapp.ui.screens.CourseDestination
import com.example.betteradminapp.ui.screens.CourseScreen
import com.example.betteradminapp.ui.screens.MainDestination
import com.example.betteradminapp.ui.screens.MainScreen
import com.example.betteradminapp.ui.screens.MessageDestination
import com.example.betteradminapp.ui.screens.MessageScreen
import com.example.betteradminapp.ui.screens.SendMessageDestination
import com.example.betteradminapp.ui.screens.SendMessageScreen
import com.example.betteradminapp.ui.screens.SettingsDestination
import com.example.betteradminapp.ui.screens.SettingsScreen

// Provides Navigation graph for the application.
@RequiresApi(Build.VERSION_CODES.O)
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
                navigateToMain = { navController.navigate(MainDestination.route) },
                navigateToCourse = { navController.navigate(CourseDestination.route) },
                navigateToMessage = { navController.navigate(MessageDestination.route) },
                navigateToEvent = { /* TODO */ },
                navigateToSettings = { navController.navigate(SettingsDestination.route) },
                navigateUp = { navController.navigateUp() }
            )
        }
        composable(route = CourseDestination.route) {
            CourseScreen(
                windowSize = windowSize,
                navigateToMain = { navController.navigate(MainDestination.route) },
                navigateToCourse = { navController.navigate(CourseDestination.route) },
                navigateToMessage = { navController.navigate(MessageDestination.route) },
                navigateToEvent = { /* TODO */ },
                navigateToSettings = { navController.navigate(SettingsDestination.route) },
                navigateUp = { navController.navigateUp() }
            )
        }
        composable(route = MessageDestination.route) {
            MessageScreen(
                windowSize = windowSize,
                navigateToMain = { navController.navigate(MainDestination.route) },
                navigateToCourse = { navController.navigate(CourseDestination.route) },
                navigateToMessage = { navController.navigate(MessageDestination.route) },
                navigateToEvent = { /* TODO */ },
                navigateToSettings = { navController.navigate(SettingsDestination.route) },
                navigateUp = { navController.navigateUp() },
                navigateToSendMessage = { navController.navigate(SendMessageDestination.route) }
            )
        }
        composable(route = SendMessageDestination.route) {
            SendMessageScreen(
                windowSize = windowSize,
                navigateUp = { navController.navigateUp() },
                navigateToMessage = { navController.navigate(MessageDestination.route) })
        }
        composable(route = SettingsDestination.route) {
            SettingsScreen(
                windowSize = windowSize,
                navigateToMain = { navController.navigate(MainDestination.route) },
                navigateToCourse = { navController.navigate(CourseDestination.route) },
                navigateToMessage = { navController.navigate(MessageDestination.route) },
                navigateToEvent = { /* TODO */ },
                navigateToSettings = { navController.navigate(SettingsDestination.route) },
                navigateUp = { navController.navigateUp() }
            )
        }
    }
}