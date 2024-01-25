package com.ewmservice.dto.event;

import com.ewmservice.model.auxiliaryEntities.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
public class EventInUpdateDto {
    @Length(max = 120, min = 3)
    private String title;
    @Length(min = 20, max = 2000)
    private String annotation;
    @Length(min = 20, max = 7000)
    private String description;
    private Boolean paid;
    private Boolean requestModeration;
    private String eventDate;
    private Integer category;
    private Location location;
    private Integer participantLimit;
    private StateEventDto stateAction;
}

