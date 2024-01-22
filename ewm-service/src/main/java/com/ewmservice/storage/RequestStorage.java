package com.ewmservice.storage;

import com.ewmservice.dto.RequestChangeDto;
import com.ewmservice.exception.NotFoundException;
import com.ewmservice.exception.RequestException;
import com.ewmservice.model.Event;
import com.ewmservice.model.Request;
import com.ewmservice.model.auxiliaryEntities.StatusRequest;
import com.ewmservice.storage.jpa.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public class RequestStorage {
    @Autowired
    RequestRepository requestRepository;

    public Request createRequest(Request request) {
        return requestRepository.save(request);
    }
    public Request getRequest(Integer requestId){
        return requestRepository.findById(requestId).orElseThrow(()->new NotFoundException("Request is not found"));
    }

    public Request cancelRequest(Request request) {
        return requestRepository.save(request);
    }

    public List<Request> getMyRequests(Integer userId) {
        return requestRepository.findAllByRequester(userId);
    }

    public List<Request> getRequestsForEvent(Integer eventId) {
        return requestRepository.findAllByEvent(eventId);
    }

    public List<Request> getRequestsByStatus(List<Integer>  requestsId, String status) {
        return requestRepository.findAllByIdAndStatus(requestsId,status);
    }

    public List<Request> updateRequests(List<Request> requests) {
        return requestRepository.saveAll(requests);
    }
}
