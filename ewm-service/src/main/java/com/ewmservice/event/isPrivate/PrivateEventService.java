package com.ewmservice.event.isPrivate;

import com.ewmservice.event.dto.EventInDto;
import com.ewmservice.event.dto.EventInUpdateDto;
import org.springframework.http.ResponseEntity;

public interface PrivateEventService {
    public ResponseEntity<Object> createEvent(EventInDto eventInDto, Integer userId);

    public ResponseEntity<Object> getMyEvents(Integer userId, Integer from, Integer size);

    public ResponseEntity<Object> getMyEvent(Integer userId, Integer eventId);

    public ResponseEntity<Object> updateMyEvent(EventInUpdateDto eventInDto, Integer userId, Integer eventId);
}
