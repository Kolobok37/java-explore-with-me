package com.ewmservice.dto.mappers;

import com.ewmservice.dto.RequestDto;
import com.ewmservice.model.Request;

public class MapperRequest {
    public  static RequestDto mapToRequestDto(Request request){
        return new RequestDto(request.getId(),request.getRequester().getId(),request.getEvent().getId(),
                request.getCreated(),request.getStatus());
    }
}
