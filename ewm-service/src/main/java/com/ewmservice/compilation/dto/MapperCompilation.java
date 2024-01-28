package com.ewmservice.compilation.dto;

import com.ewmservice.compilation.Compilation;

import java.util.ArrayList;

public class MapperCompilation {
    public static Compilation mapToCompilation(CompilationInDto compilationInDto) {
        return new Compilation(null, compilationInDto.getTitle(), compilationInDto.getPinned(), null);
    }

    public static CompilationDto mapToCompilationDtoWithoutEvent(Compilation compilation) {
        return new CompilationDto(compilation.getId(), compilation.getTitle(), compilation.getPinned(),
                new ArrayList<>());
    }
}
