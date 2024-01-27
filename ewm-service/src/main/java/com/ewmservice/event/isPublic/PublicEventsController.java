package com.ewmservice.event.isPublic;

import com.ewmservice.event.auxiliaryEntities.SortValues;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;

@Controller
@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping
public class PublicEventsController {
    @Autowired
    PublicEventServiceImpl publicEventService;

    @GetMapping("/events")
    public ResponseEntity<Object> getEventsByPublic(@RequestParam(required = false) String text,
                                                    @RequestParam(required = false) Integer[] categories,
                                                    @RequestParam(required = false) String rangeStart,
                                                    @RequestParam(required = false) String rangeEnd,
                                                    @RequestParam(required = false) SortValues sort,
                                                    @RequestParam(defaultValue = "true") Boolean onlyAvailable,
                                                    @RequestParam(required = false) Boolean paid,
                                                    @RequestParam(defaultValue = "0") Integer from,
                                                    @RequestParam(defaultValue = "10") Integer size,
                                                    HttpServletRequest request) {
        return publicEventService.getEventsByPublic(text, categories, rangeStart, rangeEnd, onlyAvailable, paid, sort,
                from, size, request);
    }

    @GetMapping("/events/{id}")
    public ResponseEntity<Object> getEventByPublic(@PathVariable @Positive Integer id, HttpServletRequest request) {
        return publicEventService.getEventByPublic(id, request);
    }
}
