package com.example.betteradminapp.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.betteradminapp.BetterAdminBottomNavigationBar
import com.example.betteradminapp.BetterAdminTopAppBar
import com.example.betteradminapp.R
import com.example.betteradminapp.ui.AppViewModelProvider
import com.example.betteradminapp.ui.navigation.NavigationDestination


object MainDestination : NavigationDestination {
    override val route = "main"
    override val titleRes = R.string.your_page
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    windowSize: WindowWidthSizeClass,
    navigateToMain: () -> Unit,
    navigateToCourse: () -> Unit,
    navigateToMessage: () -> Unit,
    navigateToEvent: () -> Unit,
    navigateToSettings: () -> Unit,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val mainUiState by viewModel.mainUiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            BetterAdminTopAppBar(
                title = stringResource(MainDestination.titleRes),
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
                currentSelected = "main"
            )
        }
    ) { innerPadding ->
        MainBody(
            mainUiState = mainUiState,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
    }
}

@Composable
fun MainBody(
    mainUiState: MainUiState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (mainUiState.userId == -1) {
            Text(text = stringResource(R.string.error_something_went_wrong))
        }
        else {
            CourseCardsRow(mainUiState = mainUiState)
        }
    }
}

@Composable
fun CourseCardsRow(
    mainUiState: MainUiState,
    modifier: Modifier = Modifier
) {
    Column() {
        Text(
            text = stringResource(R.string.enrolled_courses),
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            fontWeight = FontWeight.Bold
        )
        LazyRow() {
            items(items = mainUiState.courses ?: listOf()) { course ->
                CourseCard(
                    courseName = course.courseName,
                    modifier = modifier
                )
            }
        }
    }
}

@Composable
fun CourseCard(
    courseName: String,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier){
        Text(
            text = courseName,
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            textAlign = TextAlign.Center)

    }
}