package com.ewmservice.controller;

import com.ewmservice.dto.RequestChangeDto;
import com.ewmservice.model.Request;
import com.ewmservice.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;


@Controller
@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping
public class RequestController {
    @Autowired
    RequestService requestService;

    @PostMapping("/users/{userId}/requests")
    public ResponseEntity<Object> createRequest( @PathVariable @Positive Integer userId,
                                                @RequestParam @Positive Integer eventId) {

        return requestService.createRequest(userId,eventId);
    }

    @PatchMapping("/users/{userId}/requests/{requestId}/cancel")
    public ResponseEntity<Object> cancelRequest( @PathVariable @Positive Integer userId,
                                                 @PathVariable @Positive Integer requestId) {
        return requestService.cancelRequest(userId,requestId);
    }
    @GetMapping("/users/{userId}/requests")
    public ResponseEntity<Object> getMyRequests(@PathVariable @Positive Integer userId) {
        return requestService.getMyRequests(userId);
    }
    @GetMapping("/users/{userId}/events/{eventId}/requests")
    public ResponseEntity<Object> getRequestsForEvent(@PathVariable @Positive Integer userId,
                                                      @PathVariable @Positive Integer eventId) {
        return requestService.getRequestsForEvent(userId,eventId);
    }
    @PatchMapping("/users/{userId}/events/{eventId}/requests")
    public ResponseEntity<Object> changeStatusRequest(@PathVariable @Positive Integer userId,
                                                      @PathVariable @Positive Integer eventId,
                                                      @RequestBody RequestChangeDto requestChangeDto) {
        return requestService.changeStatusRequest(userId,eventId,requestChangeDto);
    }
}
