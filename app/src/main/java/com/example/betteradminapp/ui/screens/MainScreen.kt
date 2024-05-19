package com.example.betteradminapp.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Message
import androidx.compose.material.icons.filled.AlternateEmail
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Numbers
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.ZoomIn
import androidx.compose.material.icons.filled.ZoomOut
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.betteradminapp.BetterAdminBottomNavigationBar
import com.example.betteradminapp.BetterAdminTopAppBar
import com.example.betteradminapp.R
import com.example.betteradminapp.data.model.Pupil
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
    setUnreadMessages: (Int) -> Unit,
    unreadMessages: Int,
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val mainUiState by viewModel.mainUiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    var titleTextSize by remember { mutableFloatStateOf(32f) }
    var headerTextSize by remember { mutableFloatStateOf(12f) }
    var textSize by remember { mutableFloatStateOf(16f) }
    viewModel.unreadReceivedMessages(setUnreadMessages)

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
        floatingActionButton = {
            Row {
                FloatingActionButton(
                    shape = CircleShape,
                    onClick = {
                        titleTextSize -= 2
                        headerTextSize -= 2
                        textSize -= 2
                    },
                ) {
                    Icon(Icons.Filled.ZoomOut, "Zoom out")
                }
                Spacer(modifier = Modifier.width(8.dp))
                FloatingActionButton(
                    shape = CircleShape,
                    onClick = {
                        titleTextSize += 2
                        headerTextSize += 2
                        textSize += 2
                    },
                ) {
                    Icon(Icons.Filled.ZoomIn, "Zoom in")
                }
            }
        },

        bottomBar = {
            BetterAdminBottomNavigationBar(
                navigateToMain = navigateToMain,
                navigateToCourse = navigateToCourse,
                navigateToMessage = navigateToMessage,
                navigateToEvent = navigateToEvent,
                navigateToSettings = navigateToSettings,
                currentSelected = "main",
                unreadMessages = unreadMessages
            )
        }
    ) { innerPadding ->
        MainBody(
            mainUiState = mainUiState,
            titleTextSize = titleTextSize,
            textSize = textSize,
            headerTextSize = headerTextSize,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
    }
}

@Composable
fun MainBody(
    mainUiState: MainUiState,
    titleTextSize: Float,
    textSize: Float,
    headerTextSize: Float,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        if (mainUiState.userId == -1 || mainUiState.user == null) {
            Text(text = stringResource(R.string.error_something_went_wrong))
        }
        else {
            val coursesString = mainUiState.courses
                ?.map { course -> course.courseName }
                ?.joinToString { it }
                ?: ""
            PupilBio(
                pupil = mainUiState.user,
                coursesString = coursesString,
                titleTextSize = titleTextSize,
                textSize = textSize,
                headerTextSize = headerTextSize
            )
        }
    }
}

@Composable
fun PupilBio(
    pupil: Pupil,
    coursesString: String,
    titleTextSize: Float,
    textSize: Float,
    headerTextSize: Float,
    modifier: Modifier = Modifier
) {


    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
        ) {
            Text(
                text = "${pupil.firstName} ${pupil.lastName}",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = titleTextSize.sp
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            InfoRow(
                label = stringResource(id = R.string.email),
                value = pupil.email,
                icon = Icons.Filled.AlternateEmail,
                textSize = textSize,
                headerTextSize = headerTextSize
            )
            InfoRow(
                label = stringResource(id = R.string.phone),
                value = pupil.phoneNo,
                icon = Icons.Filled.Call,
                textSize = textSize,
                headerTextSize = headerTextSize
            )
            InfoRow(
                label = stringResource(id = R.string.enrollment_date),
                value = pupil.enrollmentDate.toString(),
                icon = Icons.Filled.CalendarMonth,
                textSize = textSize,
                headerTextSize = headerTextSize
            )
            InfoRow(
                label = stringResource(id = R.string.note),
                value = pupil.note ?: "",
                icon = Icons.Filled.Edit,
                textSize = textSize,
                headerTextSize = headerTextSize
            )
            InfoRow(
                label = stringResource(id = R.string.school),
                value = pupil.school,
                icon = Icons.Filled.School,
                textSize = textSize,
                headerTextSize = headerTextSize
            )
            InfoRow(
                label = stringResource(id = R.string.city),
                value = pupil.city,
                icon = Icons.Filled.LocationCity,
                textSize = textSize,
                headerTextSize = headerTextSize
            )
            InfoRow(
                label = stringResource(id = R.string.road),
                value = pupil.road,
                icon = Icons.Filled.Home,
                textSize = textSize,
                headerTextSize = headerTextSize
            )
            InfoRow(
                label = stringResource(id = R.string.postal_code),
                value = pupil.postalCode,
                icon = Icons.Filled.Numbers,
                textSize = textSize,
                headerTextSize = headerTextSize
            )
            InfoRow(
                label = stringResource(id = R.string.enrolled_courses),
                value = coursesString,
                icon = Icons.Filled.MusicNote,
                textSize = textSize,
                headerTextSize = headerTextSize
            )
        }
    }
}

@Composable
fun InfoRow(
    label: String,
    value: String,
    icon: ImageVector,
    textSize: Float,
    headerTextSize: Float
) {
    Row(
        modifier = Modifier
            .padding(vertical = 4.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall.copy(fontSize = headerTextSize.sp),
                color = Color.Gray
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Medium,
                    fontSize = textSize.sp
                )
            )
        }
    }
}