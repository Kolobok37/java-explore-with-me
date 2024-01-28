package com.ewmservice.event.dto;

import com.ewmservice.category.dto.MapperCategory;
import com.ewmservice.comment.dto.CommentDto;
import com.ewmservice.comment.dto.MapperComment;
import com.ewmservice.event.Event;
import com.ewmservice.event.auxiliaryEntities.StateEvent;
import com.ewmservice.request.StatusRequest;
import com.ewmservice.user.dto.MapperUser;

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
                eventInDto.getPaid(), eventInDto.getRequestModeration(), time, null, null, null,
                eventInDto.getLocation(), eventInDto.getParticipantLimit(), StateEvent.PENDING, LocalDateTime.now(),
                null, new ArrayList<>());
        return event;
    }

    public static Event mapToEvent(EventInUpdateDto eventInUpdateDto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime time = null;
        if (eventInUpdateDto.getEventDate() != null) {
            time = Optional.of(LocalDateTime.parse(eventInUpdateDto.getEventDate(), formatter)).orElse(null);
        }
        Event event = new Event(null, eventInUpdateDto.getTitle(), eventInUpdateDto.getAnnotation(),
                eventInUpdateDto.getDescription(), eventInUpdateDto.getPaid(), eventInUpdateDto.getRequestModeration(),
                time, null, null, null, eventInUpdateDto.getLocation(), eventInUpdateDto.getParticipantLimit(), null,
                LocalDateTime.now(), null, null);
        return event;
    }

    public static EventFullDto mapToEventFullDto(Event event, Integer views) {
        List<CommentDto> comments = null;
        if (event.getComments() != null) {
            comments = MapperComment.mapToCommentsDto(event.getComments());
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        EventFullDto eventFullDto = EventFullDto.builder().id(event.getId()).title(event.getTitle()).annotation(event.getAnnotation())
                .description(event.getDescription()).paid(event.getPaid()).eventDate(event.getEventDate().format(formatter))
                .requestModeration(event.getRequestModeration()).createdOn(event.getCreatedOn().format(formatter))
                .category(MapperCategory.mapToCategoryDto(event.getCategory())).state(event.getStateAction())
                .initiator(MapperUser.mapToUserShortDto(event.getInitiator())).location(event.getLocation())
                .participantLimit(event.getParticipantLimit()).comments(comments)
                .views(views).build();
        if (event.getPublishedOn() != null) {
            eventFullDto.setPublishedOn(event.getPublishedOn().format(formatter));
        }
        if (event.getRequests() != null) {
            eventFullDto.setConfirmedRequests((int) event.getRequests().stream()
                    .filter(r -> StatusRequest.CONFIRMED.toString().equals(r.getStatus()))
                    .count());
        }
        return eventFullDto;
    }

    public static EventShortDto mapToEventShortDto(EventFullDto event) {
        int quantityComments = 0;
        if (event.getComments() != null) {
            quantityComments = event.getComments().size();
        }
        return EventShortDto.builder().id(event.getId()).title(event.getTitle())
                .eventDate(event.getEventDate()).paid(event.getPaid())
                .annotation(event.getAnnotation()).category(event.getCategory())
                .confirmedRequests(event.getConfirmedRequests()).quantityComments(quantityComments)
                .initiator(event.getInitiator()).build();
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
