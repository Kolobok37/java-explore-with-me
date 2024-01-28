package com.ewmservice.event.dto;

import com.ewmservice.event.auxiliaryEntities.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
public class EventInUpdateDto {
    @Length(max = 120, min = 3)
    String title;
    @Length(min = 20, max = 2000)
    String annotation;
    @Length(min = 20, max = 7000)
    String description;
    Boolean paid;
    Boolean requestModeration;
    String eventDate;
    Integer category;
    Location location;
    Integer participantLimit;
    StateEventDto stateAction;
}

