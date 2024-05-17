package com.example.betteradminapp.ui.screens

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Nightlight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.betteradminapp.BetterAdminBottomNavigationBar
import com.example.betteradminapp.BetterAdminTopAppBar
import com.example.betteradminapp.R
import com.example.betteradminapp.ui.navigation.NavigationDestination

object SettingsDestination : NavigationDestination {
    override val route = "settings"
    override val titleRes = R.string.nav_bar_settings
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    windowSize: WindowWidthSizeClass,
    navigateToMain: () -> Unit,
    navigateToCourse: () -> Unit,
    navigateToMessage: () -> Unit,
    navigateToEvent: () -> Unit,
    navigateToSettings: () -> Unit,
    navigateUp: () -> Unit,
    logOut: () -> Unit,
    onThemeUpdated: () -> Unit,
    darkTheme: Boolean,
    unreadMessages: Int,
    modifier: Modifier = Modifier,
    //viewModel: SettingsViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            BetterAdminTopAppBar(
                title = stringResource(SettingsDestination.titleRes),
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateUp
            )
        },
        bottomBar = {
            BetterAdminBottomNavigationBar(
                navigateToMain = navigateToMain,
                navigateToCourse = navigateToCourse,
                navigateToMessage = navigateToMessage,
                navigateToEvent = navigateToEvent,
                navigateToSettings = navigateToSettings,
                currentSelected = "settings",
                unreadMessages = unreadMessages
            )
        }
    ) { innerPadding ->
        SettingsBody(
            logOut = logOut,
            onThemeUpdated = onThemeUpdated,
            darkTheme = darkTheme,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
    }
}

@Composable
fun SettingsBody(
    logOut: () -> Unit,
    onThemeUpdated: () -> Unit,
    darkTheme: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        Row(
            modifier = Modifier.padding(start = 30.dp, top = 30.dp, end = 30.dp)
        ) {
            Text(
                text = stringResource(id = R.string.log_out),
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                fontWeight= FontWeight.Bold,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
                    .wrapContentHeight(align = Alignment.CenterVertically)
            )
            CustomButton(
                onClick = logOut,
                imageVector = Icons.AutoMirrored.Filled.Logout,
                imageDescription = "Logout button"
            )
        }

        Row(
            modifier = Modifier.padding(start = 30.dp, top = 30.dp, end = 30.dp)
        ) {
            Text(
                text = stringResource(id = R.string.dark_mode),
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                fontWeight= FontWeight.Bold,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
                    .wrapContentHeight(align = Alignment.CenterVertically)
            )
            ThemeSwitcher(
                onClick = onThemeUpdated,
                darkTheme = darkTheme
            )
        }
    }
}

@Composable
fun CustomButton(
    onClick: () -> Unit,
    imageVector: ImageVector,
    imageDescription: String,
    modifier: Modifier = Modifier,
    height: Dp = 50.dp,
    width: Dp = 100.dp,
    borderWidth: Dp = 1.dp,
    parentShape: Shape = CircleShape
) {
    FilledTonalButton(onClick = onClick, modifier = Modifier
        .height(height)
        .width(width)
        .border(
            border = BorderStroke(
                width = borderWidth,
                color = MaterialTheme.colorScheme.primary
            ),
            shape = parentShape
        )) {
        Icon(
            imageVector = imageVector,
            contentDescription = imageDescription
        )
    }
}

@Composable
fun ThemeSwitcher(
    size: Dp = 50.dp,
    iconSize: Dp = size / 3,
    padding: Dp = 5.dp,
    borderWidth: Dp = 1.dp,
    parentShape: Shape = CircleShape,
    toggleShape: Shape = CircleShape,
    animationSpec: AnimationSpec<Dp> = tween(durationMillis = 300),
    onClick: () -> Unit,
    darkTheme: Boolean
) {
    val offset by animateDpAsState(
        targetValue = if (darkTheme) 0.dp else size,
        animationSpec = animationSpec, label = ""
    )

    Box(modifier = Modifier
        .width(size * 2)
        .height(size)
        .clip(shape = parentShape)
        .clickable {
            onClick()
        }
        .background(MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Box(
            modifier = Modifier
                .size(size)
                .offset(x = offset)
                .padding(all = padding)
                .clip(shape = toggleShape)
                .background(MaterialTheme.colorScheme.primary)
        ) {}
        Row(
            modifier = Modifier
                .border(
                    border = BorderStroke(
                        width = borderWidth,
                        color = MaterialTheme.colorScheme.primary
                    ),
                    shape = parentShape
                )
        ) {
            Box(
                modifier = Modifier.size(size),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.size(iconSize),
                    imageVector = Icons.Default.Nightlight,
                    contentDescription = "Theme Icon",
                    tint = if (darkTheme) MaterialTheme.colorScheme.secondaryContainer
                    else MaterialTheme.colorScheme.primary
                )
            }
            Box(
                modifier = Modifier.size(size),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.size(iconSize),
                    imageVector = Icons.Default.LightMode,
                    contentDescription = "Theme Icon",
                    tint = if (darkTheme) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.secondaryContainer
                )
            }
        }
    }
}

@Preview
@Composable
fun SettingsBodyPreview() {
    SettingsBody(
        onThemeUpdated = {},
        logOut = {},
        darkTheme = true
    )
}
