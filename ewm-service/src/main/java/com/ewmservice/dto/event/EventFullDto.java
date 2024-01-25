package com.ewmservice.dto.event;

import com.ewmservice.dto.CategoryDto;
import com.ewmservice.dto.UserShortDto;
import com.ewmservice.model.auxiliaryEntities.Location;
import com.ewmservice.model.auxiliaryEntities.StateEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class EventFullDto {
    private Integer id;
    private String title;
    private String annotation;
    private String description;
    private Boolean paid;
    private Boolean requestModeration;
    private String eventDate;
    private String createdOn;
    private CategoryDto category;
    private UserShortDto initiator;
    private Location location;
    private Integer participantLimit;
    private Integer confirmedRequests;
    private Integer views;
    private StateEvent state;
    private String publishedOn;
}
