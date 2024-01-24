package com.ewmservice.service;

import com.ewmservice.Paging;
import com.ewmservice.client.EventClient;
import com.ewmservice.dto.dto.AppDto;
import com.ewmservice.dto.dto.HitDto;
import com.ewmservice.dto.event.EventFullDto;
import com.ewmservice.dto.event.EventInDto;
import com.ewmservice.dto.event.EventInUpdateDto;
import com.ewmservice.dto.event.StateEventDto;
import com.ewmservice.dto.mappers.MapperEvent;
import com.ewmservice.exception.NotFoundException;
import com.ewmservice.exception.PublishEventException;
import com.ewmservice.exception.UpdateEntityException;
import com.ewmservice.exception.ValidationDataException;
import com.ewmservice.model.Event;
import com.ewmservice.model.auxiliaryEntities.SortValues;
import com.ewmservice.model.auxiliaryEntities.StateEvent;
import com.ewmservice.storage.CategoryStorage;
import com.ewmservice.storage.EventStorage;
import com.ewmservice.storage.UserStorage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventService {
    @Autowired
    EventStorage eventStorage;
    @Autowired
    UserStorage userStorage;
    @Autowired
    CategoryStorage categoryStorage;
    ObjectMapper objectMapper = new ObjectMapper();
    Gson gson = new GsonBuilder().create();
    @Autowired
    private EventClient eventClient;

    public ResponseEntity<Object> createEvent(EventInDto eventInDto, Integer userId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime eventDate = LocalDateTime.parse(eventInDto.getEventDate(), formatter);
        if (eventDate.isBefore(LocalDateTime.now().plusHours(2))) {
            throw new ValidationDataException("Field: eventDate. Error: должно содержать дату, " +
                    "которая еще не наступила. Value: " + eventInDto.getEventDate());
        }
        Event event = MapperEvent.mapToEvent(eventInDto);
        event.setEventDate(eventDate);
        event.setInitiator(userStorage.getUser(userId));
        event.setCategory(categoryStorage.getCategory(eventInDto.getCategory())
                .orElseThrow(() -> new NotFoundException(String.format("Category with id=%d was not found",
                        eventInDto.getCategory()))));
        if (eventInDto.getPaid() == null) {
            event.setPaid(false);
        }
        if (eventInDto.getParticipantLimit() == null) {
            event.setParticipantLimit(0);
        }
        if (eventInDto.getRequestModeration() == null) {
            event.setRequestModeration(true);
        }
        EventFullDto eventFullDto = MapperEvent.mapToEventFullDto(eventStorage.createEvent(event), 0);
        return new ResponseEntity<>(eventFullDto, HttpStatus.CREATED);
    }

    public ResponseEntity<Object> getMyEvents(Integer userId, Integer from, Integer size) {
        List<EventFullDto> eventFullDto = eventStorage.getMyEvents(userId, Paging.paging(from, size)).stream()
                .map(e -> MapperEvent.mapToEventFullDto(e, getSizeViewsByEvent(List.of("/events/" + e.getId()))))
                .collect(Collectors.toList());
        return new ResponseEntity<>(eventFullDto, HttpStatus.OK);
    }

    public ResponseEntity<Object> getMyEvent(Integer userId, Integer eventId) {
        Event event = eventStorage.getMyEvent(userId, eventId);
        return new ResponseEntity<>(MapperEvent.mapToEventFullDto(event,
                getSizeViewsByEvent(List.of("/events/" + event.getId()))), HttpStatus.OK);
    }

    public ResponseEntity<Object> updateMyEvent(EventInUpdateDto eventInDto, Integer userId, Integer eventId) {
        Event oldEvent = eventStorage.getMyEvent(userId, eventId);
        if (StateEvent.PUBLISHED.equals(oldEvent.getStateAction())) {
            throw new UpdateEntityException("Only pending or canceled events can be changed");
        }
        if (eventInDto.getEventDate() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime eventDate = LocalDateTime.parse(eventInDto.getEventDate(), formatter);
            if (eventDate.isBefore(LocalDateTime.now().plusHours(2))) {
                throw new ValidationDataException("Field: eventDate. Error: должно содержать дату, " +
                        "которая еще не наступила. Value: " + eventInDto.getEventDate());
            }
        }
        Event newEvent = MapperEvent.mapToEvent(eventInDto);
        MapperEvent.updateEvent(newEvent, oldEvent);
        if (StateEventDto.CANCEL_REVIEW.equals(eventInDto.getStateAction())) {
            oldEvent.setStateAction(StateEvent.CANCELED);
        }
        if (StateEventDto.SEND_TO_REVIEW.equals(eventInDto.getStateAction())
                && StateEvent.REJECTED.equals(oldEvent.getStateAction())) {
            oldEvent.setStateAction(StateEvent.PENDING);
        }
        return new ResponseEntity<>(MapperEvent.mapToEventFullDto(eventStorage.updateEvent(oldEvent),
                getSizeViewsByEvent(List.of("/events/" + oldEvent.getId()))), HttpStatus.OK);
    }

    public ResponseEntity<Object> getEventsByAdmin(Integer[] users, String[] states, Integer[] categories,
                                                   String rangeStart, String rangeEnd, Integer from,
                                                   Integer size) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startTime;
        LocalDateTime endTime;
        if (rangeStart != null) {
            startTime = LocalDateTime.parse(rangeStart, formatter);
        } else {
            startTime = LocalDateTime.of(1400, 1, 1, 0, 0);
        }
        if (rangeEnd != null) {
            endTime = LocalDateTime.parse(rangeEnd, formatter);
        } else {
            endTime = LocalDateTime.of(9999, 12, 31, 23, 59);
        }
        if (startTime.isAfter(endTime)) {
            throw new ValidationDataException("Date is not valid");
        }
        List<Event> events = eventStorage.getEventsByAdmin(users, categories, startTime, endTime,
                Paging.paging(from, size));
        List<EventFullDto> eventFullDto = events.stream()
                .map(e -> MapperEvent.mapToEventFullDto(e, getSizeViewsByEvent(List.of("/events/" + e.getId()))))
                .collect(Collectors.toList());
        return new ResponseEntity<>(eventFullDto, HttpStatus.OK);
    }

    public ResponseEntity<Object> updateEventByAdmin(EventInUpdateDto eventInDto, Integer eventId) {
        Event oldEvent = eventStorage.getEvent(eventId);
        if (StateEvent.PUBLISHED.equals(oldEvent.getStateAction())) {
            throw new UpdateEntityException("Only pending or canceled events can be changed");
        }
        if (eventInDto.getEventDate() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime eventDate = LocalDateTime.parse(eventInDto.getEventDate(), formatter);
            if (eventDate.isBefore(LocalDateTime.now().plusHours(1))) {
                throw new ValidationDataException("Field: eventDate. Error: должно содержать дату, " +
                        "которая еще не наступила. Value: " + eventInDto.getEventDate());
            }
        }
        Event newEvent = MapperEvent.mapToEvent(eventInDto);
        MapperEvent.updateEvent(newEvent, oldEvent);
        if (StateEventDto.PUBLISH_EVENT.equals(eventInDto.getStateAction())
                && StateEvent.PENDING.equals(oldEvent.getStateAction())) {
            oldEvent.setStateAction(StateEvent.PUBLISHED);
            oldEvent.setPublishedOn(LocalDateTime.now());
        } else if (StateEventDto.REJECT_EVENT.equals(eventInDto.getStateAction())
                && StateEvent.PENDING.equals(oldEvent.getStateAction())) {
            oldEvent.setStateAction(StateEvent.REJECTED);
        } else if (StateEventDto.SEND_TO_REVIEW.equals(eventInDto.getStateAction())
                && (StateEvent.CANCELED.equals(oldEvent.getStateAction())
                || (StateEvent.REJECTED.equals(oldEvent.getStateAction())))) {
            oldEvent.setStateAction(StateEvent.PENDING);
        } else if (StateEventDto.PUBLISH_EVENT.equals(eventInDto.getStateAction())
                && (StateEvent.REJECTED.equals(oldEvent.getStateAction()))) {
            throw new PublishEventException("You can't publish rejected event.");
        }
        return new ResponseEntity<>(MapperEvent.mapToEventFullDto(eventStorage.updateEvent(oldEvent),
                getSizeViewsByEvent(List.of("/events/" + oldEvent.getId()))), HttpStatus.OK);
    }

    public ResponseEntity<Object> getEventsByPublic(String text, Integer[] categories, String rangeStart,
                                                    String rangeEnd, Boolean onlyAvailable, Boolean paid,
                                                    SortValues sort, Integer from, Integer size,
                                                    HttpServletRequest request) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startTime;
        LocalDateTime endTime;
        if (rangeStart != null) {
            startTime = LocalDateTime.parse(rangeStart, formatter);
        } else {
            startTime = LocalDateTime.of(1400, 1, 1, 0, 0);
        }
        if (rangeEnd != null) {
            endTime = LocalDateTime.parse(rangeEnd, formatter);
        } else {
            endTime = LocalDateTime.of(9999, 12, 31, 23, 59);
        }
        if (startTime.isAfter(endTime)) {
            throw new ValidationDataException("Date is not valid");
        }
        List<EventFullDto> eventFullDto = eventStorage.getEventsByPublic(text, categories, startTime, endTime,
                        Paging.paging(from, size)).stream()
                .map(e -> MapperEvent.mapToEventFullDto(e, getSizeViewsByEvent(List.of("/events/" + e.getId()))))
                .collect(Collectors.toList());
        if (paid != null) {
            eventFullDto = eventFullDto.stream().filter(e -> e.getPaid() == paid).collect(Collectors.toList());
        }
        if (onlyAvailable) {
            eventFullDto = eventFullDto.stream()
                    .filter(r -> r.getConfirmedRequests() < r.getParticipantLimit()).collect(Collectors.toList());
        }
        if (sort == SortValues.EVENT_DATE) {
            eventFullDto = eventFullDto.stream().sorted(Comparator.comparing(EventFullDto::getEventDate))
                    .collect(Collectors.toList());
        }
        if (sort == SortValues.VIEWS) {
            eventFullDto = eventFullDto.stream().sorted(Comparator.comparing(EventFullDto::getViews))
                    .collect(Collectors.toList());
        }
        eventClient.addHit(new HitDto("/events", "/events", request.getRemoteAddr(), null));
        return new ResponseEntity<>(eventFullDto, HttpStatus.OK);
    }

    public ResponseEntity<Object> getEventByPublic(Integer id, HttpServletRequest request) {
        Event event = eventStorage.getEvent(id);
        if (event.getStateAction() != StateEvent.PUBLISHED) {
            throw new NotFoundException("Event dot'n publish.");
        }
        eventClient.addHit(new HitDto("/events/" + id, "/events/" + id,
                request.getRemoteAddr(), null));
        return new ResponseEntity<>(MapperEvent
                .mapToEventFullDto(event, getSizeViewsByEvent(List.of("/events/" + id))), HttpStatus.OK);
    }

    public List<AppDto> getAllViewsByEvents(List<String> uris) {
        List<AppDto> obj1 = eventClient.getStats(uris);
        return obj1;
    }

    public Integer getSizeViewsByEvent(List<String> uris) {
        List<AppDto> list = getAllViewsByEvents(uris);
        if (list.size() != 0) {
            return list.get(0).getHits();
        }
        return 0;
    }
}
