package com.ewmservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestDto {
    Integer id;
    Integer requester;
    Integer event;
    String created;
    String status;
}
