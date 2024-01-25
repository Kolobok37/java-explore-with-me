package com.ewmservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestChangeDto {
    Integer[] requestIds;
    String status;
}
