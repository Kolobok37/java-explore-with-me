package com.ewmservice.dto.mappers;

import com.ewmservice.dto.request.RequestDto;
import com.ewmservice.dto.request.RequestStatusUpdateDto;
import com.ewmservice.model.Request;
import com.ewmservice.model.auxiliaryEntities.StatusRequest;

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
