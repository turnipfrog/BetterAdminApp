package com.example.betteradminapp.data.local

import com.example.betteradminapp.data.EventRepository
import com.example.betteradminapp.data.model.Event
import kotlinx.coroutines.flow.Flow

class OfflineEventRepository(private val eventDao: EventDao): EventRepository {
    override fun getAllEventsStream(): Flow<List<Event>> = eventDao.getAllEvents()

    override fun getEventByIdStream(id: Int): Flow<Event?> = eventDao.getEventById(id)

    override suspend fun insertEvent(event: Event) = eventDao.insert(event)

    override suspend fun deleteEvent(event: Event) = eventDao.delete(event)

    override suspend fun updateEvent(event: Event) = eventDao.update(event)
}