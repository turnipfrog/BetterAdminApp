package com.example.betteradminapp.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.betteradminapp.data.EventRepository
import com.example.betteradminapp.data.model.Event
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class EventViewModel(val eventRepository: EventRepository): ViewModel() {

    val eventUiState: StateFlow<EventUiState> =
        eventRepository.getAllEventsStream().map { EventUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = EventUiState()
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }


//    init {
//        viewModelScope.launch {
//                eventRepository.insertEvent(
//                Event(
//                    title = "Strengekvartet ved Rådhuset",
//                    description = "Kun for strengentusiaster!\n" +
//                            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
//                            "Quisque metus felis, tempor sed arcu ultricies, accumsan efficitur quam. " +
//                            "Etiam sed libero eget eros vehicula fringilla in sit amet quam.",
//                    eventDate = Date(124, 4, 30),
//                    startTime = "17.30",
//                    endTime = "20.00"
//                )
//            )
//            eventRepository.insertEvent(
//                Event(
//                    title = "Koncert i Koncertsalen",
//                    description = "Alle er velkommen.\n " +
//                            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
//                            "Quisque metus felis, tempor sed arcu ultricies, accumsan efficitur quam. " +
//                            "Etiam sed libero eget eros vehicula fringilla in sit amet quam.",
//                    eventDate = Date(124, 4, 17),
//                    startTime = "18.00",
//                    endTime = "20.00"
//                )
//            )
//            eventRepository.insertEvent(
//                Event(
//                    title = "Koncert i Koncertsalen",
//                    description = "Alle er velkommen.\n " +
//                            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
//                            "Quisque metus felis, tempor sed arcu ultricies, accumsan efficitur quam. " +
//                            "Etiam sed libero eget eros vehicula fringilla in sit amet quam.",
//                    eventDate = Date(124, 4, 22),
//                    startTime = "20.00",
//                    endTime = "22.00"
//                )
//            )
//            eventRepository.insertEvent(
//                Event(
//                    title = "Koncert i Koncertsalen",
//                    description = "Alle er velkommen.\n " +
//                            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
//                            "Quisque metus felis, tempor sed arcu ultricies, accumsan efficitur quam. " +
//                            "Etiam sed libero eget eros vehicula fringilla in sit amet quam.",
//                    eventDate = Date(124, 4, 3),
//                    startTime = "18.00",
//                    endTime = "20.00"
//                )
//            )
//            eventRepository.insertEvent(
//                Event(
//                    title = "Koncert i Koncertsalen",
//                    description = "Alle er velkommen.\n " +
//                            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
//                            "Quisque metus felis, tempor sed arcu ultricies, accumsan efficitur quam. " +
//                            "Etiam sed libero eget eros vehicula fringilla in sit amet quam.",
//                    eventDate = Date(124, 5, 3),
//                    startTime = "18.00",
//                    endTime = "20.00"
//                )
//            )
//            eventRepository.insertEvent(
//                Event(
//                    title = "Koncert i Koncertsalen",
//                    description = "Alle er velkommen.\n " +
//                            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
//                            "Quisque metus felis, tempor sed arcu ultricies, accumsan efficitur quam. " +
//                            "Etiam sed libero eget eros vehicula fringilla in sit amet quam.",
//                    eventDate = Date(124, 5, 15),
//                    startTime = "18.00",
//                    endTime = "20.00"
//                )
//            )
//            eventRepository.insertEvent(
//                Event(
//                    title = "Koncert i Koncertsalen",
//                    description = "Alle er velkommen.\n " +
//                            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
//                            "Quisque metus felis, tempor sed arcu ultricies, accumsan efficitur quam. " +
//                            "Etiam sed libero eget eros vehicula fringilla in sit amet quam.",
//                    eventDate = Date(124, 4, 25),
//                    startTime = "18.00",
//                    endTime = "20.00"
//                )
//            )
//            eventRepository.insertEvent(
//                Event(
//                    title = "Koncert i Koncertsalen",
//                    description = "Alle er velkommen.\n " +
//                            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
//                            "Quisque metus felis, tempor sed arcu ultricies, accumsan efficitur quam. " +
//                            "Etiam sed libero eget eros vehicula fringilla in sit amet quam.",
//                    eventDate = Date(124, 4, 28),
//                    startTime = "18.00",
//                    endTime = "20.00"
//                )
//            )
//        }
//    }
}

data class EventUiState(val events: List<Event> = listOf())