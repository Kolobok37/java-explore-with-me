package com.ewmservice.compilation.dto;

import com.ewmservice.event.dto.EventShortDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Data
@AllArgsConstructor
public class CompilationDto {
    Integer id;
    @Length(min = 1, max = 50)
    String title;
    Boolean pinned;
    List<EventShortDto> events;
}
