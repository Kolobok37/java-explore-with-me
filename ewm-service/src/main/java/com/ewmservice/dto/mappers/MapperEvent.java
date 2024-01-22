package com.ewmservice.dto.mappers;

import com.ewmservice.dto.event.EventFullDto;
import com.ewmservice.dto.event.EventInDto;
import com.ewmservice.dto.event.EventInUpdateDto;
import com.ewmservice.dto.event.EventShortDto;
import com.ewmservice.model.Event;
import com.ewmservice.model.Request;
import com.ewmservice.model.auxiliaryEntities.StateEvent;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MapperEvent {
    public static Event mapToEvent(EventInDto eventInDto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime time = null;
        if (eventInDto.getEventDate() != null) {
            time = Optional.of(LocalDateTime.parse(eventInDto.getEventDate(), formatter)).orElse(null);
        }
        Event event = new Event(null, eventInDto.getTitle(), eventInDto.getAnnotation(), eventInDto.getDescription(),
                eventInDto.getPaid(), eventInDto.getRequestModeration(), time, null, null, null, eventInDto.getLocation(),
                eventInDto.getParticipantLimit(), StateEvent.PENDING, LocalDateTime.now());
        return event;
    }

    public static Event mapToEvent(EventInUpdateDto EventInUpdateDto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime time = null;
        if (EventInUpdateDto.getEventDate() != null) {
            time = Optional.of(LocalDateTime.parse(EventInUpdateDto.getEventDate(), formatter)).orElse(null);
        }
        Event event = new Event(null, EventInUpdateDto.getTitle(), EventInUpdateDto.getAnnotation(), EventInUpdateDto.getDescription(),
                EventInUpdateDto.getPaid(), EventInUpdateDto.getRequestModeration(), time, null, null, null, EventInUpdateDto.getLocation(),
                EventInUpdateDto.getParticipantLimit(), null, LocalDateTime.now());
        return event;
    }

    public static EventFullDto mapToEventFullDto(Event event) {//views
        Optional<List<Request>> requestList = Optional.ofNullable(event.getRequests());
        List<Request> requests = requestList.orElse(new ArrayList<>());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return EventFullDto.builder().id(event.getId()).title(event.getTitle()).annotation(event.getAnnotation())
                .description(event.getDescription()).paid(event.getPaid()).eventDate(event.getEventDate().format(formatter))
                .requestModeration(event.getRequestModeration()).createdOn(event.getCreatedOn().format(formatter))
                .category(MapperCategory.mapToCategoryDto(event.getCategory())).state(event.getStateAction())
                .initiator(MapperUser.mapToUserShortDto(event.getInitiator())).location(event.getLocation())
                .participantLimit(event.getParticipantLimit())
                .confirmedRequests(1)
                .views(1).build();
    }

    public static EventShortDto mapToEventShortDto(Event event){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return EventShortDto.builder().id(event.getId()).title(event.getTitle())
                .eventDate(event.getEventDate().format(formatter)).paid(event.getPaid())
                .annotation(event.getAnnotation()).category(MapperCategory.mapToCategoryDto(event.getCategory()))
                .confirmedRequests(event.getApprovedRequest().size())
                .initiator(MapperUser.mapToUserShortDto(event.getInitiator())).views(1).build();
    }

    public static void updateEvent(Event newEvent, Event oldEvent) {
        if (newEvent.getTitle() != null) {
            oldEvent.setTitle(newEvent.getTitle());
        }
        if (newEvent.getAnnotation() != null) {
            oldEvent.setAnnotation(newEvent.getAnnotation());
        }
        if (newEvent.getDescription() != null) {
            oldEvent.setDescription(newEvent.getDescription());
        }
        if (newEvent.getPaid() != null) {
            oldEvent.setPaid(newEvent.getPaid());
        }
        if (newEvent.getRequestModeration() != null) {
            oldEvent.setRequestModeration(newEvent.getRequestModeration());
        }
        if (newEvent.getEventDate() != null) {
            oldEvent.setEventDate(newEvent.getEventDate());
        }
        if (newEvent.getTitle() != null) {
            oldEvent.setTitle(newEvent.getTitle());
        }
        if (newEvent.getCategory() != null) {
            oldEvent.setCategory(newEvent.getCategory());
        }
        if (newEvent.getLocation() != null) {
            oldEvent.setLocation(newEvent.getLocation());
        }
        if (newEvent.getParticipantLimit() != null) {
            oldEvent.setParticipantLimit(newEvent.getParticipantLimit());
        }
    }
}
