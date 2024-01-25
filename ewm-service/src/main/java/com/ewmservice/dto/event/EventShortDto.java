package com.ewmservice.dto.event;

import com.ewmservice.dto.CategoryDto;
import com.ewmservice.dto.UserShortDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class EventShortDto {
    private Integer id;
    private String title;
    private String annotation;
    private  Boolean paid;
    private  String eventDate;
    private   CategoryDto category;
    private   UserShortDto initiator;
    private    Integer confirmedRequests;
    private    Integer views;
}
