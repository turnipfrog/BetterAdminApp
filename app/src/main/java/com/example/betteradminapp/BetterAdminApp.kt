package com.example.betteradminapp

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.betteradminapp.ui.navigation.BetterAdminNavHost

/**
 * Top level composable that represents screens for the application.
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BetterAdminApp(
    windowSize: WindowWidthSizeClass,
    navController: NavHostController = rememberNavController()
) {
    BetterAdminNavHost(
        windowSize = windowSize,
        navController = navController
    )
}

/**
 * App bar to display title and conditionally display the back navigation.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BetterAdminTopAppBar(
    title: String,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigateUp: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = { Text(title) },
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

/**
 * Bottom Navigation Bar for navigation when logged in
 */
@Composable
fun BetterAdminBottomNavigationBar(
    navigateToMain: () -> Unit,
    navigateToSettings: () -> Unit,
) {
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf("Main", "Courses", "Messages", "Events", "Settings")

    var iconImg: ImageVector = Icons.Filled.Home
    var labelStr = ""
    var navigateFunc: () -> Unit = {}

    NavigationBar {
        items.forEachIndexed { index, item ->
            when (item) {
                "Main" -> {
                    iconImg = Icons.Filled.Home
                    labelStr = stringResource(R.string.nav_bar_main)
                    navigateFunc = navigateToMain
                }
                "Courses" -> {
                    iconImg = Icons.Filled.MusicNote
                    labelStr = stringResource(R.string.nav_bar_courses)
                }
                "Messages" -> {
                    iconImg = Icons.Filled.Mail
                    labelStr = stringResource(R.string.nav_bar_messages)
                }
                "Events" -> {
                    iconImg = Icons.Filled.Event
                    labelStr = stringResource(R.string.nav_bar_events)
                }
                "Settings" -> {
                    iconImg = Icons.Filled.Settings
                    labelStr = stringResource(R.string.nav_bar_settings)
                    navigateFunc = navigateToSettings
                }
            }
            NavigationBarItem(
                icon = { Icon(imageVector = iconImg, contentDescription = labelStr) },
                label = { Text(labelStr) },
                selected = selectedItem == index,
                onClick = { selectedItem = index }
            )
        }
    }
}