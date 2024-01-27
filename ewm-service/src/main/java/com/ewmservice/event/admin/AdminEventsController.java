package com.ewmservice.event.admin;

import com.ewmservice.event.dto.EventInUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping
public class AdminEventsController {
    @Autowired
    AdminEventServiceImpl adminEventService;

    @PatchMapping("/admin/events/{eventId}")
    public ResponseEntity<Object> updateEventByAdmin(@RequestBody @Valid EventInUpdateDto eventInDto, @PathVariable Integer eventId) {
        return adminEventService.updateEventByAdmin(eventInDto, eventId);
    }

    @GetMapping("/admin/events")
    public ResponseEntity<Object> getEventsByAdmin(@RequestParam(required = false) Integer[] users,
                                                   @RequestParam(required = false) String[] states,
                                                   @RequestParam(required = false) Integer[] categories,
                                                   @RequestParam(required = false) String rangeStart,
                                                   @RequestParam(required = false) String rangeEnd,
                                                   @RequestParam(defaultValue = "0") Integer from,
                                                   @RequestParam(defaultValue = "10") Integer size) {
        return adminEventService.getEventsByAdmin(users, states, categories, rangeStart, rangeEnd, from, size);
    }
}
