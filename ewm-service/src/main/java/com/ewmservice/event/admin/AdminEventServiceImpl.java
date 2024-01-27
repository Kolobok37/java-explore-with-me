package com.ewmservice.event.admin;

import com.ewmservice.Paging;
import com.ewmservice.event.Event;
import com.ewmservice.event.EventService;
import com.ewmservice.event.auxiliaryEntities.StateEvent;
import com.ewmservice.event.dto.EventFullDto;
import com.ewmservice.event.dto.EventInUpdateDto;
import com.ewmservice.event.dto.MapperEvent;
import com.ewmservice.event.dto.StateEventDto;
import com.ewmservice.event.storage.EventStorage;
import com.ewmservice.exception.PublishEventException;
import com.ewmservice.exception.UpdateEntityException;
import com.ewmservice.exception.ValidationDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class AdminEventServiceImpl extends EventService implements AdminEventService {
    @Autowired
    EventStorage eventStorage;

    public ResponseEntity<Object> getEventsByAdmin(Integer[] users, String[] states, Integer[] categories,
                                                   String rangeStart, String rangeEnd, Integer from,
                                                   Integer size) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startTime = rangeStart != null ? LocalDateTime.parse(rangeStart, formatter)
                : LocalDateTime.of(1400, 1, 1, 0, 0);
        LocalDateTime endTime = rangeEnd != null ? LocalDateTime.parse(rangeEnd, formatter)
                : LocalDateTime.of(9999, 12, 31, 23, 59);
        if (startTime.isAfter(endTime)) {
            throw new ValidationDataException("Date is not valid");
        }
        List<Event> events = eventStorage.getEventsByAdmin(users, categories, startTime, endTime,
                Paging.paging(from, size));
        List<EventFullDto> eventFullDto = distributionViewByEvents(events);
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
        return new ResponseEntity<>(distributionViewByEvents(List.of(eventStorage.updateEvent(oldEvent))).get(0),
                HttpStatus.OK);
    }

}
