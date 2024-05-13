package com.example.betteradminapp.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.betteradminapp.BetterAdminTopAppBar
import com.example.betteradminapp.R
import com.example.betteradminapp.ui.AppViewModelProvider
import com.example.betteradminapp.ui.navigation.NavigationDestination

object SendMessageDestination : NavigationDestination {
    override val route = "sendmessage"
    override val titleRes = R.string.new_message
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SendMessageScreen(
    windowSize: WindowWidthSizeClass,
    navigateUp: () -> Unit,
    navigateToMessage: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SendMessageViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val sendMessageUiState by viewModel.sendMessageUiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            BetterAdminTopAppBar(
                title = stringResource(SendMessageDestination.titleRes),
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateUp
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                shape = CircleShape,
                onClick = { /* TODO: Navigate to MessageScreen and insert message into database -IF- message valid */ },
            ) {
                Icon(Icons.AutoMirrored.Filled.Send, "Floating action button.")
            }
        },
        floatingActionButtonPosition = FabPosition.End,
    ) { innerPadding ->
        SendMessageBody(
            sendMessageUiState = sendMessageUiState,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
    }
}

@Composable
fun SendMessageBody(
    sendMessageUiState: SendMessageUiState,
    modifier: Modifier = Modifier
) {

}