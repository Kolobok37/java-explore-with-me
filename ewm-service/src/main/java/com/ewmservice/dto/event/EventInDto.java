package com.ewmservice.dto.event;

import com.ewmservice.model.auxiliaryEntities.Location;
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
    private String title;
    @NotBlank(message = "Field: annotation. Error: must not be blank. Value: null")
    @Length(min = 20, max = 2000)
    private String annotation;
    @NotBlank(message = "Field: description. Error: must not be blank. Value: null")
    @Length(min = 20, max = 7000)
    private String description;
    private  Boolean paid;
    private Boolean requestModeration;
    @NotBlank(message = "Field: eventDate. Error: must not be blank. Value: null")
    private String eventDate;
    private  Integer category;
    @NotNull(message = "Field: location. Error: must not be blank. Value: null")
    private Location location;
    private Integer participantLimit;
    private StateEventDto stateAction;
}
