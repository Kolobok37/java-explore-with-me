package com.ewmservice.event.isPublic;

import com.ewmservice.event.auxiliaryEntities.SortValues;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface PublicEventService {
    ResponseEntity<Object> getEventsByPublic(String text, Integer[] categories, String rangeStart,
                                             String rangeEnd, Boolean onlyAvailable, Boolean paid,
                                             SortValues sort, Integer from, Integer size,
                                             HttpServletRequest request);

    ResponseEntity<Object> getEventByPublic(Integer id, HttpServletRequest request);
}
