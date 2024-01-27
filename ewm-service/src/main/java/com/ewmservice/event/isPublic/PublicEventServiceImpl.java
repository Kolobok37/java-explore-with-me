package com.ewmservice.event.isPublic;

import com.ewmservice.Paging;
import com.ewmservice.category.storage.CategoryStorage;
import com.ewmservice.event.Event;
import com.ewmservice.event.EventService;
import com.ewmservice.event.auxiliaryEntities.SortValues;
import com.ewmservice.event.auxiliaryEntities.StateEvent;
import com.ewmservice.event.client.EventClient;
import com.ewmservice.event.client.statsDto.HitDto;
import com.ewmservice.event.dto.EventFullDto;
import com.ewmservice.event.storage.EventStorage;
import com.ewmservice.exception.NotFoundException;
import com.ewmservice.exception.ValidationDataException;
import com.ewmservice.user.storage.UserStorage;
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
public class PublicEventServiceImpl extends EventService implements PublicEventService {
    @Autowired
    EventStorage eventStorage;
    @Autowired
    UserStorage userStorage;
    @Autowired
    CategoryStorage categoryStorage;
    @Autowired
    EventClient eventClient;

    public ResponseEntity<Object> getEventsByPublic(String text, Integer[] categories, String rangeStart,
                                                    String rangeEnd, Boolean onlyAvailable, Boolean paid,
                                                    SortValues sort, Integer from, Integer size,
                                                    HttpServletRequest request) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startTime = rangeStart != null ? LocalDateTime.parse(rangeStart, formatter)
                : LocalDateTime.of(1400, 1, 1, 0, 0);
        LocalDateTime endTime = rangeEnd != null ? LocalDateTime.parse(rangeEnd, formatter)
                : LocalDateTime.of(9999, 12, 31, 23, 59);
        if (startTime.isAfter(endTime)) {
            throw new ValidationDataException("Date is not valid");
        }
        List<EventFullDto> eventFullDto = distributionViewByEvents(eventStorage.getEventsByPublic(text, categories, startTime, endTime,
                Paging.paging(from, size)));
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
        } else if (sort == SortValues.VIEWS) {
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
        return new ResponseEntity<>(distributionViewByEvents(List.of(event)).get(0), HttpStatus.OK);
    }
}
