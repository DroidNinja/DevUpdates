package me.arunsharma.devupdates.utils

import com.dev.services.models.ServiceItem
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

interface EventBus {
    suspend fun produceEvent(event: Event)
    fun observe(): SharedFlow<Event>
}

class EventBusImpl @Inject constructor() : EventBus {
    private val _events = MutableSharedFlow<Event>() // private mutable shared flow

    override suspend fun produceEvent(event: Event) {
        _events.emit(event) // suspends until all subscribers receive it
    }

    override fun observe(): SharedFlow<Event> {
        return _events.asSharedFlow() // publicly exposed as read-only shared flow
    }
}

sealed class Event

class BookmarkEvent(val item: ServiceItem) : Event()
