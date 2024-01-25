package com.ewmservice.dto.request;

import com.ewmservice.dto.request.RequestDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RequestStatusUpdateDto {
    List<RequestDto> confirmedRequests;
    List<RequestDto> rejectedRequests;

}
