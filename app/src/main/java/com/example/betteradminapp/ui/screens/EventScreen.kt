package com.example.betteradminapp.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.betteradminapp.BetterAdminBottomNavigationBar
import com.example.betteradminapp.BetterAdminTopAppBar
import com.example.betteradminapp.R
import com.example.betteradminapp.data.model.Event
import com.example.betteradminapp.ui.AppViewModelProvider
import com.example.betteradminapp.ui.navigation.NavigationDestination
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Locale

object EventDestination : NavigationDestination {
    override val route = "event"
    override val titleRes = R.string.nav_bar_events
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventScreen(
    windowSize: WindowWidthSizeClass,
    navigateToMain: () -> Unit,
    navigateToCourse: () -> Unit,
    navigateToMessage: () -> Unit,
    navigateToEvent: () -> Unit,
    navigateToSettings: () -> Unit,
    navigateUp: () -> Unit,
    unreadMessages: Int,
    modifier: Modifier = Modifier,
    viewModel: EventViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val eventUiState by viewModel.eventUiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            BetterAdminTopAppBar(
                title = stringResource(EventDestination.titleRes),
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
                currentSelected = "event",
                unreadMessages = unreadMessages
            )
        }
    ) { innerPadding ->
        EventBody(
            eventUiState = eventUiState,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
    }
}

@Composable
fun EventBody(
    eventUiState: EventUiState,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        CalendarWithEvents(eventUiState.events)
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun CalendarWithEvents(events: List<Event>) {
    // MutableState for current month
    val currentMonth = remember { mutableStateOf(Calendar.getInstance()) }

    // MutableState for selected date events
    val selectedDateEvents = remember { mutableStateOf<List<Event>>(listOf()) }

    // Remember the PagerState
    val pagerState = rememberPagerState(initialPage = Int.MAX_VALUE / 2)

    val scope = rememberCoroutineScope()

    // UI Layout
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Display month and year
        val month = currentMonth.value.clone() as Calendar
        month.add(Calendar.MONTH, pagerState.currentPage - Int.MAX_VALUE / 2)
        Text(
            text = "${month.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale("da"))} ${month.get(Calendar.YEAR)}",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Calendar controls
        Row(
            horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = {
                scope.launch {
                    pagerState.scrollToPage(pagerState.currentPage - 1)
                }
            }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Previous Month")
            }
            IconButton(onClick = {
                scope.launch {
                    pagerState.scrollToPage(pagerState.currentPage + 1)
                }
            }) {
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Next Month")
            }
        }

        // HorizontalPager for calendar
        HorizontalPager(
            count = Int.MAX_VALUE,
            modifier = Modifier.fillMaxSize(),
            state = pagerState,
            verticalAlignment = Alignment.Top
        ) { page ->
            val pagerMonth = currentMonth.value.clone() as Calendar
            pagerMonth.add(Calendar.MONTH, page - Int.MAX_VALUE / 2)

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Calendar grid with events
                val daysInMonth = pagerMonth.getActualMaximum(Calendar.DAY_OF_MONTH)
                val days = (1..daysInMonth).toList()
                CalendarGrid(events, pagerMonth, selectedDateEvents)

                // Display events for selected date at the bottom
                EventList(selectedDateEvents.value)
            }
        }
    }
}

@Composable
fun CalendarGrid(events: List<Event>, currentMonth: Calendar, selectedDateEvents: MutableState<List<Event>>) {
    val daysInMonth = currentMonth.getActualMaximum(Calendar.DAY_OF_MONTH)
    val firstDayOfMonth = currentMonth.clone() as Calendar
    firstDayOfMonth.set(Calendar.DAY_OF_MONTH, 1)
    val startingDayOfWeek = firstDayOfMonth.get(Calendar.DAY_OF_WEEK) - 1 // Adjusting for zero-based indexing
    val days = (1..daysInMonth).toList()
    val paddingDaysBefore = List(startingDayOfWeek) { -1 }
    val paddingDaysAfter = List((7 - (startingDayOfWeek + daysInMonth) % 7) % 7) { -1 }
    val allDays = paddingDaysBefore + days + paddingDaysAfter
    LazyVerticalGrid(columns = GridCells.Fixed(7)) {
        items(allDays.size) { index ->
            val day = allDays[index]
            val textColor = if (day <= 0) Color.Transparent else MaterialTheme.colorScheme.onSecondaryContainer
            val currentDay = if (day <= 0) "" else day.toString()
            // Determine the events for this day
            val eventsForDay = events.filter { event ->
                val eventCalendar = Calendar.getInstance()
                eventCalendar.time = event.eventDate
                eventCalendar.get(Calendar.DAY_OF_MONTH) == day && eventCalendar.get(Calendar.MONTH) == currentMonth.get(
                    Calendar.MONTH
                )
            }
            // Display dots for each event
            val eventDots = buildString {
                repeat(eventsForDay.size) {
                    append("• ")
                }
            }
            // Display the day and event dots
            Column(
                modifier = Modifier
                    .padding(2.dp)
                    .clickable {
                        // On day click, update selected date events
                        selectedDateEvents.value = eventsForDay
                    }, horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = currentDay,
                    color = textColor,
                )
                Text(
                    text = eventDots,
                    color = Color.Red,
                )
            }
        }
    }
}

@Composable
fun EventList(events: List<Event>) {
    Column(
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        events.forEach { event ->
            Column {
                Text(
                    modifier = Modifier,
                    text = event.title,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.padding(bottom = 10.dp))
                Text(
                    text = "${event.startTime} - ${event.endTime}",
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.padding(bottom = 30.dp))
                Text(text = event.description)
            }

        }
    }
}