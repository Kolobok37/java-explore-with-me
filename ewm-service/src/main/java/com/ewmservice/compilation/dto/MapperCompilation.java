package com.ewmservice.compilation.dto;

import com.ewmservice.compilation.Compilation;
import com.ewmservice.event.dto.MapperEvent;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class MapperCompilation {
    public static Compilation mapToCompilation(CompilationInDto compilationInDto) {
        return new Compilation(null, compilationInDto.getTitle(), compilationInDto.getPinned(), null);
    }

    public static CompilationDto mapToCompilationDto(Compilation compilation) {
        return new CompilationDto(compilation.getId(), compilation.getTitle(), compilation.getPinned(),
                compilation.getEvents().stream().map(MapperEvent::mapToEventShortDto).collect(Collectors.toList()));
    }

    public static CompilationDto mapToCompilationDtoWithoutEvent(Compilation compilation) {
        return new CompilationDto(compilation.getId(), compilation.getTitle(), compilation.getPinned(),
                new ArrayList<>());
    }
}
