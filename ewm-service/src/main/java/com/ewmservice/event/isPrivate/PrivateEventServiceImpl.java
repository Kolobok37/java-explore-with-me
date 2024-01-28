package com.ewmservice.event.isPrivate;

import com.ewmservice.Paging;
import com.ewmservice.category.storage.CategoryStorage;
import com.ewmservice.event.Event;
import com.ewmservice.event.EventService;
import com.ewmservice.event.auxiliaryEntities.StateEvent;
import com.ewmservice.event.client.EventClient;
import com.ewmservice.event.dto.*;
import com.ewmservice.event.storage.EventStorage;
import com.ewmservice.exception.NotFoundException;
import com.ewmservice.exception.UpdateEntityException;
import com.ewmservice.exception.ValidationDataException;
import com.ewmservice.user.storage.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class PrivateEventServiceImpl extends EventService implements PrivateEventService {
    @Autowired
    EventStorage eventStorage;
    @Autowired
    UserStorage userStorage;
    @Autowired
    CategoryStorage categoryStorage;
    @Autowired
    EventClient eventClient;

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
        EventFullDto eventFullDto = distributionViewByEvents(List.of(eventStorage.createEvent(event))).get(0);
        return new ResponseEntity<>(eventFullDto, HttpStatus.CREATED);
    }

    public ResponseEntity<Object> getMyEvents(Integer userId, Integer from, Integer size) {
        List<EventFullDto> eventFullDto = distributionViewByEvents(eventStorage
                .getMyEvents(userId, Paging.paging(from, size)));
        return new ResponseEntity<>(eventFullDto, HttpStatus.OK);
    }

    public ResponseEntity<Object> getMyEvent(Integer userId, Integer eventId) {
        Event event = eventStorage.getMyEvent(userId, eventId);
        return new ResponseEntity<>(distributionViewByEvents(List.of(event)).get(0), HttpStatus.OK);
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
        return new ResponseEntity<>(distributionViewByEvents(List.of(eventStorage.updateEvent(oldEvent))).get(0),
                HttpStatus.OK);
    }
}
