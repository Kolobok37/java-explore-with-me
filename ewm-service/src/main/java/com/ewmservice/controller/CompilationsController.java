package com.ewmservice.controller;

import com.ewmservice.dto.CompilationInDto;
import com.ewmservice.dto.CompilationUpdateDto;
import com.ewmservice.service.CompilationsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@Controller
@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping
public class CompilationsController {
    @Autowired
    CompilationsService compilationsService;

    @PostMapping("/admin/compilations")
    public ResponseEntity<Object> createCompilation(@RequestBody @Valid CompilationInDto compilationInDto) {
        return compilationsService.createCompilation(compilationInDto);
    }

    @DeleteMapping("/admin/compilations/{compId}")
    public ResponseEntity<Object> deleteCompilation(@PathVariable @Positive Integer compId) {
        return compilationsService.deleteCompilation(compId);
    }

    @PatchMapping("/admin/compilations/{compId}")
    public ResponseEntity<Object> updateCompilation(@RequestBody @Valid CompilationUpdateDto compilationInDto,
                                                    @PathVariable @Positive Integer compId) {
        return compilationsService.updateCompilation(compilationInDto, compId);
    }

    @GetMapping("/compilations")
    public ResponseEntity<Object> getCompilations(@RequestParam(defaultValue = "false") Boolean pinned,
                                                  @RequestParam(defaultValue = "0") Integer from,
                                                  @RequestParam(defaultValue = "10") Integer size) {
        return compilationsService.getCompilations(pinned, from, size);
    }

    @GetMapping("/compilations/{compId}")
    public ResponseEntity<Object> getCompilations(@PathVariable @Positive Integer compId) {
        return compilationsService.getCompilation(compId);
    }
}
