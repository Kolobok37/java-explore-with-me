package com.ewmservice.request.storage;

import com.ewmservice.exception.NotFoundException;
import com.ewmservice.request.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RequestStorage {
    @Autowired
    RequestRepository requestRepository;

    public Request createRequest(Request request) {
        return requestRepository.save(request);
    }

    public Request getRequest(Integer requestId) {
        return requestRepository.findById(requestId).orElseThrow(() -> new NotFoundException("Request is not found"));
    }

    public Request cancelRequest(Request request) {
        return requestRepository.save(request);
    }

    public List<Request> getMyRequests(Integer userId) {
        return requestRepository.findAllByRequesterId(userId);
    }

    public List<Request> getRequestsForEvent(Integer eventId) {
        return requestRepository.findAllByEventId(eventId);
    }

    public List<Request> getRequestsByStatus(List<Integer> requestsId) {
        return requestRepository.findAllById(requestsId);
    }

    public List<Request> updateRequests(List<Request> requests) {
        return requestRepository.saveAll(requests);
    }
}
