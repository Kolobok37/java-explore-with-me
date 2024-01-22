package com.ewmservice.service;

import com.ewmservice.Paging;
import com.ewmservice.dto.event.EventFullDto;
import com.ewmservice.dto.event.EventInDto;
import com.ewmservice.dto.event.EventInUpdateDto;
import com.ewmservice.dto.event.StateEventDto;
import com.ewmservice.dto.mappers.MapperEvent;
import com.ewmservice.exception.NotFoundException;
import com.ewmservice.exception.UpdateEntityException;
import com.ewmservice.exception.ValidationDataException;
import com.ewmservice.model.Event;
import com.ewmservice.model.auxiliaryEntities.StateEvent;
import com.ewmservice.storage.CategoryStorage;
import com.ewmservice.storage.EventStorage;
import com.ewmservice.storage.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventService {
    @Autowired
    EventStorage eventStorage;
    @Autowired
    UserStorage userStorage;
    @Autowired
    CategoryStorage categoryStorage;

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
        EventFullDto eventFullDto = MapperEvent.mapToEventFullDto(eventStorage.createEvent(event));
        return new ResponseEntity<>(eventFullDto, HttpStatus.CREATED);
    }

    public ResponseEntity<Object> getMyEvents(Integer userId, Integer from, Optional<Integer> size) {
        List<EventFullDto> eventFullDto = eventStorage.getMyEvents(userId, Paging.paging(from, size)).stream()
                .map(MapperEvent::mapToEventFullDto).collect(Collectors.toList());
        return new ResponseEntity<>(eventFullDto, HttpStatus.OK);
    }

    public ResponseEntity<Object> getMyEvent(Integer userId, Integer eventId) {
        return new ResponseEntity<>(MapperEvent.mapToEventFullDto(eventStorage.getMyEvent(userId, eventId)),
                HttpStatus.OK);
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
        if(StateEventDto.CANCEL_REVIEW.equals(eventInDto.getStateAction())){
            oldEvent.setStateAction(StateEvent.CANCELED);
        }
        return new ResponseEntity<>(MapperEvent.mapToEventFullDto(eventStorage.updateEvent(oldEvent)),
                HttpStatus.OK);
    }

    public ResponseEntity<Object> getEventsByAdmin(Integer[] users, String[] states, Integer[] categories,
                                                   String rangeStart, String rangeEnd, Integer from,
                                                   Optional<Integer> size) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startTime = Optional.of(LocalDateTime.parse(rangeStart, formatter)).orElse(LocalDateTime.MIN);
        LocalDateTime endTime = Optional.of(LocalDateTime.parse(rangeEnd, formatter)).orElse(LocalDateTime.MAX);
        List<Event> events = eventStorage.getEventsByAdmin(users,
                Arrays.stream(states).collect(Collectors.toList()), categories, startTime, endTime,
                Paging.paging(from, size));
        List<EventFullDto> eventFullDto = events.stream()
                .map(MapperEvent::mapToEventFullDto).collect(Collectors.toList());
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
        if(StateEventDto.PUBLISH_EVENT.equals(eventInDto.getStateAction())
                &&StateEvent.PENDING.equals(oldEvent.getStateAction())){
            oldEvent.setStateAction(StateEvent.PUBLISHED);
        }
        else if(StateEventDto.REJECT_EVENT.equals(eventInDto.getStateAction())
                &&StateEvent.PENDING.equals(oldEvent.getStateAction())){
            oldEvent.setStateAction(StateEvent.REJECTED);
        }
        else if(StateEventDto.SEND_TO_REVIEW.equals(eventInDto.getStateAction())
                &&(StateEvent.CANCELED.equals(oldEvent.getStateAction())
                ||(StateEvent.REJECTED.equals(oldEvent.getStateAction())))){
            oldEvent.setStateAction(StateEvent.PENDING);
        }
        return new ResponseEntity<>(MapperEvent.mapToEventFullDto(eventStorage.updateEvent(oldEvent)),
                HttpStatus.OK);
    }

    public ResponseEntity<Object> getEventsByPublic(String text, Integer[] categories, String rangeStart,
                                                    String rangeEnd, Boolean onlyAvailable, Boolean paid,
                                                    Integer from, Optional<Integer> size) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startTime = Optional.of(LocalDateTime.parse(rangeStart, formatter)).orElse(LocalDateTime.MIN);
        LocalDateTime endTime = Optional.of(LocalDateTime.parse(rangeEnd, formatter)).orElse(LocalDateTime.MAX);

        List<EventFullDto> eventFullDto = eventStorage.getEventsByPublic(text, categories, startTime, endTime
                        , paid, Paging.paging(from, size)).stream()
                .map(MapperEvent::mapToEventFullDto).collect(Collectors.toList());

        //фильтрация по лимиту участников
        return new ResponseEntity<>(eventFullDto, HttpStatus.OK);
    }

    public ResponseEntity<Object> getEventByPublic(Integer id) {
        Event event = eventStorage.getEvent(id);
        if (event.getStateAction() != StateEvent.PUBLISHED) {
            throw new NotFoundException("Event dot'n publish.");
        }
        return new ResponseEntity<>(MapperEvent.mapToEventFullDto(event), HttpStatus.OK);
    }
}
