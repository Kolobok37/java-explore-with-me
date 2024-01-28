package com.ewmservice.request.isPrivate;

import com.ewmservice.request.dto.RequestChangeDto;
import org.springframework.http.ResponseEntity;

public interface PrivateRequestService {
    ResponseEntity<Object> createRequest(Integer userId, Integer eventId);

    ResponseEntity<Object> cancelRequest(Integer userId, Integer requestId);

    ResponseEntity<Object> getMyRequests(Integer userId);

    ResponseEntity<Object> getRequestsForEvent(Integer userId, Integer eventId);

    ResponseEntity<Object> changeStatusRequest(Integer userId, Integer eventId, RequestChangeDto requestChangeDto);
}
