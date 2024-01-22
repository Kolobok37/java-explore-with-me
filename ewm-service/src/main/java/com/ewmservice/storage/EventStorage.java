package com.ewmservice.storage;

import com.ewmservice.dto.CompilationInDto;
import com.ewmservice.exception.NotFoundException;
import com.ewmservice.model.Event;
import com.ewmservice.model.auxiliaryEntities.StateEvent;
import com.ewmservice.storage.jpa.EventRepository;
import com.ewmservice.storage.jpa.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class EventStorage {
    @Autowired
    LocationRepository locationRepository;
    @Autowired
    EventRepository eventRepository;

    public Event createEvent(Event event) {
        locationRepository.save(event.getLocation());
        return eventRepository.save(event);
    }

    public List<Event> getMyEvents(Integer userId, Pageable paging) {
        return eventRepository.findByInitiatorById(userId,paging);
    }

    public Event getMyEvent(Integer userId, Integer eventId) {
        Event event = Optional.of(eventRepository.getById(eventId))
                .orElseThrow(()->new NotFoundException(String.format("Event with id=%d was not found", eventId)));
        if(!Objects.equals(event.getInitiator().getId(), userId)){
            throw new NotFoundException(String.format("User id=%d is not initiator event id=%d",userId,eventId));
        }
        return event;
    }

    public Event updateEvent(Event event) {
        locationRepository.save(event.getLocation());
        return eventRepository.save(event);
    }

    public List<Event> getEventsByAdmin(Integer[] users, List<String> states, Integer[] categories,
                                        LocalDateTime rangeStart, LocalDateTime rangeEnd, Pageable paging) {
        List<Event> events = eventRepository.getEventsByAdmin(users,categories,rangeStart,rangeEnd,paging);
        return events; // фильтрация по статусу
    }

    public Event getEvent(Integer eventId) {
        return eventRepository.findById(eventId).orElseThrow(()->new NotFoundException("Event not found"));
    }

    public List<Event> getEventsByPublic(String text, Integer[] categories, LocalDateTime startTime,
                                                LocalDateTime endTime, Boolean paid, Pageable paging) {
        return eventRepository.getEventsByPublic(text,categories,startTime,endTime,paid,paging);
    }

    public List<Event> getEventsById(List<Integer> eventsId) {
        return eventRepository.findAllById(eventsId);
    }
}
