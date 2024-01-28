package com.ewmservice.request.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RequestStatusUpdateDto {
    List<RequestDto> confirmedRequests;
    List<RequestDto> rejectedRequests;

}
