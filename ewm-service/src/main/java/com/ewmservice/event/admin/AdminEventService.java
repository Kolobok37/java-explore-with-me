package com.ewmservice.event.admin;

import com.ewmservice.event.client.statsDto.AppDto;
import com.ewmservice.event.dto.EventInUpdateDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AdminEventService {
    public ResponseEntity<Object> getEventsByAdmin(Integer[] users, String[] states, Integer[] categories,
                                                   String rangeStart, String rangeEnd, Integer from,
                                                   Integer size);

    public ResponseEntity<Object> updateEventByAdmin(EventInUpdateDto eventInDto, Integer eventId);


    public List<AppDto> getAllViewsByEvents(List<String> uris);
}
