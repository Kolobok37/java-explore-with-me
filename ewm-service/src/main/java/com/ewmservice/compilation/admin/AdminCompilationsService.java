package com.ewmservice.compilation.admin;

import com.ewmservice.compilation.dto.CompilationInDto;
import com.ewmservice.compilation.dto.CompilationUpdateDto;
import org.springframework.http.ResponseEntity;

public interface AdminCompilationsService {

    public ResponseEntity<Object> createCompilation(CompilationInDto compilationInDto);

    public ResponseEntity<Object> deleteCompilation(Integer compId);

    public ResponseEntity<Object> updateCompilation(CompilationUpdateDto compilationInDto, Integer compId);
}
