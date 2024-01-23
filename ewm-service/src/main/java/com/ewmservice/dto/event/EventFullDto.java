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
    Integer id;
    String title;
    String annotation;
    String description;
    Boolean paid;
    Boolean requestModeration;
    String eventDate;
    String createdOn;
    CategoryDto category;
    UserShortDto initiator;
    Location location;
    Integer participantLimit;
    Integer confirmedRequests;
    Integer views;
    StateEvent state;
    String publishedOn;
}
