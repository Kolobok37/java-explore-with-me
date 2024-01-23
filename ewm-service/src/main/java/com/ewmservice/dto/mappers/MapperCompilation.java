package com.ewmservice.dto.mappers;

import com.ewmservice.dto.CompilationDto;
import com.ewmservice.dto.CompilationInDto;
import com.ewmservice.model.Compilation;

import java.util.stream.Collectors;

public class MapperCompilation {
    public static Compilation mapToCompilation(CompilationInDto compilationInDto) {
        return new Compilation(null, compilationInDto.getTitle(), compilationInDto.getPinned(), null);
    }

    public static CompilationDto mapToCompilationDto(Compilation compilation) {
        return new CompilationDto(compilation.getId(), compilation.getTitle(), compilation.getPinned(),
                compilation.getEvents().stream().map(MapperEvent::mapToEventShortDto).collect(Collectors.toList()));
    }
}