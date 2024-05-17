package com.example.betteradminapp

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.betteradminapp.ui.navigation.BetterAdminNavHost

/**
 * Top level composable that represents screens for the application.
 */
@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BetterAdminApp(
    windowSize: WindowWidthSizeClass,
    onThemeUpdated: () -> Unit,
    darkTheme: Boolean,
    setUnreadMessages: (Int) -> Unit,
    unreadMessages: Int,
    navController: NavHostController = rememberNavController()
) {
    BetterAdminNavHost(
        windowSize = windowSize,
        onThemeUpdated = onThemeUpdated,
        darkTheme = darkTheme,
        setUnreadMessages = setUnreadMessages,
        unreadMessages = unreadMessages,
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
    navigateToCourse: () -> Unit,
    navigateToMessage: () -> Unit,
    navigateToEvent: () -> Unit,
    navigateToSettings: () -> Unit,
    unreadMessages: Int,
    currentSelected: String
) {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(imageVector = Icons.Filled.Home, contentDescription = stringResource(R.string.nav_bar_main)) },
            label = { Text(stringResource(R.string.nav_bar_main)) },
            selected = currentSelected == "main",
            onClick = navigateToMain
        )
        NavigationBarItem(
            icon = { Icon(imageVector = Icons.Filled.MusicNote, contentDescription = stringResource(R.string.nav_bar_courses)) },
            label = { Text(stringResource(R.string.nav_bar_courses)) },
            selected = currentSelected == "course",
            onClick = navigateToCourse
        )
        NavigationBarItem(
            icon = { 
                BadgedBox(
                    badge = {
                        if (unreadMessages > 0) {
                            Badge {
                                Text(text = unreadMessages.toString())
                            }
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Mail,
                        contentDescription = stringResource(R.string.nav_bar_messages)
                    )
                }
            },
            label = { Text(stringResource(R.string.nav_bar_messages)) },
            selected = currentSelected == "message",
            onClick = navigateToMessage
        )
        NavigationBarItem(
            icon = { Icon(imageVector = Icons.Filled.Event, contentDescription = stringResource(R.string.nav_bar_events)) },
            label = { Text(stringResource(R.string.nav_bar_events)) },
            selected = currentSelected == "event",
            onClick = navigateToEvent
        )
        NavigationBarItem(
            icon = { Icon(imageVector = Icons.Filled.Settings, contentDescription = stringResource(R.string.nav_bar_settings)) },
            label = { Text(stringResource(R.string.nav_bar_settings)) },
            selected = currentSelected == "settings",
            onClick = navigateToSettings
        )
    }
}