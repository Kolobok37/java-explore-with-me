package com.ewmservice.compilation.isPublic;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;

@Controller
@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping
public class PublicCompilationsController {
    @Autowired
    PublicCompilationsServiceImpl publicCompilationsService;


    @GetMapping("/compilations")
    public ResponseEntity<Object> getCompilations(@RequestParam(defaultValue = "false") Boolean pinned,
                                                  @RequestParam(defaultValue = "0") Integer from,
                                                  @RequestParam(defaultValue = "10") Integer size) {
        return publicCompilationsService.getCompilations(pinned, from, size);
    }

    @GetMapping("/compilations/{compId}")
    public ResponseEntity<Object> getCompilations(@PathVariable @Positive Integer compId) {
        return publicCompilationsService.getCompilation(compId);
    }
}