package com.ewmservice.event.dto;

import com.ewmservice.category.dto.CategoryDto;
import com.ewmservice.user.dto.UserShortDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.JoinColumn;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class EventShortDto {
    Integer id;
    String title;
    String annotation;
    Boolean paid;
    String eventDate;
    CategoryDto category;
    UserShortDto initiator;
    Integer confirmedRequests;
    Integer views;
    Integer quantityComments;
}
