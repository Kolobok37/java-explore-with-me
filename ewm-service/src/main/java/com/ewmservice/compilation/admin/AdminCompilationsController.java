package com.ewmservice.compilation.admin;

import com.ewmservice.compilation.dto.CompilationInDto;
import com.ewmservice.compilation.dto.CompilationUpdateDto;
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
public class AdminCompilationsController {
    @Autowired
    AdminCompilationsServiceImpl adminCompilationsService;

    @PostMapping("/admin/compilations")
    public ResponseEntity<Object> createCompilation(@RequestBody @Valid CompilationInDto compilationInDto) {
        return adminCompilationsService.createCompilation(compilationInDto);
    }

    @DeleteMapping("/admin/compilations/{compId}")
    public ResponseEntity<Object> deleteCompilation(@PathVariable @Positive Integer compId) {
        return adminCompilationsService.deleteCompilation(compId);
    }

    @PatchMapping("/admin/compilations/{compId}")
    public ResponseEntity<Object> updateCompilation(@RequestBody @Valid CompilationUpdateDto compilationInDto,
                                                    @PathVariable @Positive Integer compId) {
        return adminCompilationsService.updateCompilation(compilationInDto, compId);
    }

}
