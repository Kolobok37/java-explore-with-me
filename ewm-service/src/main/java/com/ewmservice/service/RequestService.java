package com.ewmservice.service;

import com.ewmservice.dto.RequestChangeDto;
import com.ewmservice.dto.RequestDto;
import com.ewmservice.dto.mappers.MapperRequest;
import com.ewmservice.exception.RequestException;
import com.ewmservice.exception.ValidationDataException;
import com.ewmservice.model.Event;
import com.ewmservice.model.Request;
import com.ewmservice.model.auxiliaryEntities.StateEvent;
import com.ewmservice.model.auxiliaryEntities.StatusRequest;
import com.ewmservice.storage.EventStorage;
import com.ewmservice.storage.RequestStorage;
import com.ewmservice.storage.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class RequestService {
    @Autowired
    RequestStorage requestStorage;
    @Autowired
    EventStorage eventStorage;
    @Autowired
    UserStorage userStorage;

    public ResponseEntity<Object> createRequest(Integer userId, Integer eventId) {
        Request request = new Request();
        Event event = eventStorage.getEvent(eventId);
        request.setEvent(event);
        if (Objects.equals(event.getInitiator().getId(), userId)) {
            throw new RequestException("Initiator can't send request.");
        }
        if (!StateEvent.PUBLISHED.equals(event.getStateAction())) {
            throw new RequestException("You can send request only publish event.");
        }
        if (event.getParticipantLimit() <= event.getApprovedRequest().size() && event.getParticipantLimit() != 0) {
            throw new RequestException("The limit of participants has ended");
        }
        if (event.getRequests().stream().anyMatch(request1 -> Objects.equals(request1.getRequester().getId(), userId))) {
            throw new RequestException("Duplicate request.");
        }
        if (event.getRequestModeration()) {
            request.setStatus("CONFIRMED");
        } else {
            request.setStatus("PENDING");
        }
        request.setRequester(userStorage.getUser(userId));
        request.setCreated(LocalDateTime.now());
        RequestDto requestDto = MapperRequest.mapToRequestDto(requestStorage.createRequest(request));

        return new ResponseEntity<>(requestDto, HttpStatus.CREATED);
    }

    public ResponseEntity<Object> cancelRequest(Integer userId, Integer requestId) {
        Request request = requestStorage.getRequest(requestId);
        if (!Objects.equals(request.getRequester().getId(), userId)) {
            throw new RequestException("You can only cancel your request.");
        }
        request.setStatus("CANCEL");
        RequestDto requestDto = MapperRequest.mapToRequestDto(requestStorage.cancelRequest(request));
        return new ResponseEntity<>(requestDto, HttpStatus.OK);
    }

    public ResponseEntity<Object> getMyRequests(Integer userId) {
        userStorage.getUser(userId);
        List<RequestDto> requestDto = requestStorage.getMyRequests(userId).stream().map(MapperRequest::mapToRequestDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(requestDto, HttpStatus.OK);

    }

    public ResponseEntity<Object> getRequestsForEvent(Integer userId, Integer eventId) {
        userStorage.getUser(userId);
        List<RequestDto> requestDto = requestStorage.getRequestsForEvent(eventId).stream().map(MapperRequest::mapToRequestDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(requestDto, HttpStatus.OK);
    }

    public ResponseEntity<Object> changeStatusRequest(Integer userId, Integer eventId,
                                                      RequestChangeDto requestChangeDto) {
        List<Request> requests = requestStorage.getRequestsByStatus(requestChangeDto.getRequestIds(),
                "PENDING");
        Event event = eventStorage.getEvent(eventId);
        if (requestChangeDto.getStatus().equals(StatusRequest.REJECTED.toString())) {
            for (int i = 0; i < requests.size(); i++) {
                requests.get(i).setStatus("REJECTED");
            }
        } else if (requestChangeDto.getStatus().equals(StatusRequest.CONFIRMED.toString())) {
            int requestsSize = event.getApprovedRequest().size();
            if (event.getParticipantLimit() <= requestsSize) {
                throw new RequestException("The limit of participants has ended");

            }
            for (int i = 0; i < requests.size(); i++) {
                requests.get(i).setStatus("CONFIRMED");
                requestsSize++;
                if (event.getParticipantLimit() == requestsSize) {
                    for (int j = i; j < requests.size(); j++) {
                        requests.get(j).setStatus("REJECTED");
                    }
                    break;
                }
            }
        }
        else{
            throw  new ValidationDataException("Status not valid.");
        }
        return new ResponseEntity<>(requestStorage.updateRequests(requests)
                .stream().map(MapperRequest::mapToRequestDto), HttpStatus.OK);
    }
}
