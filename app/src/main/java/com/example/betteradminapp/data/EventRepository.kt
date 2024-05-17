package com.example.betteradminapp.data

import com.example.betteradminapp.data.model.Event
import kotlinx.coroutines.flow.Flow

interface EventRepository {
    fun getAllEventsStream(): Flow<List<Event>>

    fun getEventByIdStream(id: Int): Flow<Event?>

    suspend fun insertEvent(event: Event)

    suspend fun deleteEvent(event: Event)

    suspend fun updateEvent(event: Event)
}