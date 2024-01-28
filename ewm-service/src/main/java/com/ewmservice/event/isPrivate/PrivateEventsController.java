package com.ewmservice.event.isPrivate;

import com.ewmservice.event.dto.EventInDto;
import com.ewmservice.event.dto.EventInUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@Controller
@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping
public class PrivateEventsController {
    @Autowired
    PrivateEventServiceImpl privateEventService;

    @PostMapping("/users/{userId}/events")
    public ResponseEntity<Object> createEvent(@RequestBody @Valid EventInDto eventInDto,
                                              @PathVariable Integer userId) {
        return privateEventService.createEvent(eventInDto, userId);
    }

    @GetMapping("/users/{userId}/events")
    public ResponseEntity<Object> getMyEvents(@RequestParam(defaultValue = "0") Integer from,
                                              @RequestParam(defaultValue = "10") Integer size,
                                              @PathVariable @Positive Integer userId) {
        return privateEventService.getMyEvents(userId, from, size);
    }

    @GetMapping("/users/{userId}/events/{eventId}")
    public ResponseEntity<Object> getEvent(@PathVariable @Positive Integer eventId,
                                           @PathVariable @Positive Integer userId) {
        return privateEventService.getMyEvent(userId, eventId);
    }

    @PatchMapping("/users/{userId}/events/{eventId}")
    public ResponseEntity<Object> updateEvent(@RequestBody @Valid EventInUpdateDto eventInDto, @PathVariable Integer eventId,
                                              @PathVariable Integer userId) {
        return privateEventService.updateMyEvent(eventInDto, userId, eventId);
    }
}
