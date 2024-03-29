package com.ewmservice.event.dto;

import com.ewmservice.event.auxiliaryEntities.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class EventInDto {
    @NotBlank(message = "Field: title. Error: must not be blank. Value: null")
    @Length(max = 120, min = 3)
    String title;
    @NotBlank(message = "Field: annotation. Error: must not be blank. Value: null")
    @Length(min = 20, max = 2000)
    String annotation;
    @NotBlank(message = "Field: description. Error: must not be blank. Value: null")
    @Length(min = 20, max = 7000)
    String description;
    Boolean paid;
    Boolean requestModeration;
    @NotBlank(message = "Field: eventDate. Error: must not be blank. Value: null")
    String eventDate;
    Integer category;
    @NotNull(message = "Field: location. Error: must not be blank. Value: null")
    Location location;
    Integer participantLimit;
    StateEventDto stateAction;
}
