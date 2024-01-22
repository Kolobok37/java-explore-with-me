package com.ewmservice.controller;

import com.ewmservice.dto.event.EventInDto;
import com.ewmservice.dto.event.EventInUpdateDto;
import com.ewmservice.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping
public class EventsController {
    @Autowired
    EventService eventService;

    @PostMapping("/users/{userId}/events")
    public ResponseEntity<Object> createEvent(@RequestBody @Valid EventInDto eventInDto,
                                              @PathVariable Integer userId) {
        return eventService.createEvent(eventInDto, userId);
    }

    @GetMapping("/users/{userId}/events")
    public ResponseEntity<Object> getMyEvents(@RequestParam(defaultValue = "0") Integer from,
                                              @RequestParam(defaultValue = "10") Optional<Integer> size,
                                              @PathVariable @Positive Integer userId) {
        return eventService.getMyEvents(userId, from, size);
    }

    @GetMapping("/users/{userId}/events/{eventId}")
    public ResponseEntity<Object> getEvent(@PathVariable @Positive Integer eventId,
                                           @PathVariable @Positive Integer userId) {
        return eventService.getMyEvent(userId, eventId);
    }

    @PatchMapping("/users/{userId}/events/{eventId}")
    public ResponseEntity<Object> updateEvent(@RequestBody @Valid EventInUpdateDto eventInDto, @PathVariable Integer eventId,
                                              @PathVariable Integer userId) {
        return eventService.updateMyEvent(eventInDto, userId, eventId);
    }

    @PatchMapping("/admin/events/{eventId}")
    public ResponseEntity<Object> updateEventByAdmin(@RequestBody @Valid EventInUpdateDto eventInDto, @PathVariable Integer eventId) {
        return eventService.updateEventByAdmin(eventInDto, eventId);
    }

    @GetMapping("/admin/events")
    public ResponseEntity<Object> getEventsByAdmin(@RequestParam(required = false) Integer[] users,
                                                   @RequestParam(required = false) String[] states,
                                                   @RequestParam(required = false) Integer[] categories,
                                                   @RequestParam(required = false) String rangeStart,
                                                   @RequestParam(required = false) String rangeEnd,
                                                   @RequestParam(defaultValue = "0") Integer from,
                                                   @RequestParam(defaultValue = "10") Optional<Integer> size) {
        return eventService.getEventsByAdmin(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @GetMapping("/events")
    public ResponseEntity<Object> getEventsByPublic(@RequestParam(required = false) String text,
                                                    @RequestParam(required = false) Integer[] categories,
                                                    @RequestParam(required = false) String rangeStart,
                                                    @RequestParam(required = false) String rangeEnd,
                                                    @RequestParam(defaultValue = "true") Boolean onlyAvailable,
                                                    @RequestParam(defaultValue = "false") Boolean paid,
                                                    @RequestParam(defaultValue = "0") Integer from,
                                                    @RequestParam(defaultValue = "10") Optional<Integer> size) {
        return eventService.getEventsByPublic(text, categories, rangeStart, rangeEnd, onlyAvailable, paid, from, size);
    }

    @GetMapping("/events/{id}")
    public ResponseEntity<Object> getEventByPublic(@PathVariable @Positive Integer id) {
        return eventService.getEventByPublic(id);
    }
}
