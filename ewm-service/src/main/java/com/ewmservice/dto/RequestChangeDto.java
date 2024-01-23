package com.ewmservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestChangeDto {
    Integer[] requestIds;
    String status;
}
