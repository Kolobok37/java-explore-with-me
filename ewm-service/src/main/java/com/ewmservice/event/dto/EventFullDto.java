package com.ewmservice.event.dto;

import com.ewmservice.category.dto.CategoryDto;
import com.ewmservice.comment.dto.CommentDto;
import com.ewmservice.event.auxiliaryEntities.Location;
import com.ewmservice.event.auxiliaryEntities.StateEvent;
import com.ewmservice.user.dto.UserShortDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

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
    List<CommentDto> comments;
}
