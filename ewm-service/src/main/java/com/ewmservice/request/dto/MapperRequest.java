package com.ewmservice.request.dto;

import com.ewmservice.request.Request;
import com.ewmservice.request.StatusRequest;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MapperRequest {
    public static RequestDto mapToRequestDto(Request request) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return new RequestDto(request.getId(), request.getRequester().getId(), request.getEvent().getId(),
                request.getCreated().format(formatter), request.getStatus());
    }

    public static RequestStatusUpdateDto mapToRequestStatusUpdateDto(List<Request> requests) {
        List<RequestDto> confirmedRequests = new ArrayList<>();
        List<RequestDto> rejectedRequests = new ArrayList<>();
        for (Request request : requests) {
            if (StatusRequest.CONFIRMED.toString().equals(request.getStatus())) {
                confirmedRequests.add(MapperRequest.mapToRequestDto(request));
            }
            if (StatusRequest.REJECTED.toString().equals(request.getStatus())) {
                rejectedRequests.add(MapperRequest.mapToRequestDto(request));
            }
        }
        return new RequestStatusUpdateDto(confirmedRequests, rejectedRequests);
    }
}
