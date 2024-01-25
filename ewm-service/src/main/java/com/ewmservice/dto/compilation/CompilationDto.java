package com.ewmservice.dto.compilation;

import com.ewmservice.dto.event.EventShortDto;
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
