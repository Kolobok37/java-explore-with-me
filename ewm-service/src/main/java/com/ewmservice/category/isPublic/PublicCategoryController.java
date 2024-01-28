package com.ewmservice.category.isPublic;

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
public class PublicCategoryController {
    @Autowired
    PublicCategoryServiceImpl publicCategoryServiceImpl;

    @GetMapping("/categories")
    public ResponseEntity<Object> getCategories(@RequestParam(defaultValue = "0") Integer from,
                                                @RequestParam(defaultValue = "10") Integer size) {
        return publicCategoryServiceImpl.getCategories(from, size);
    }

    @GetMapping("/categories/{catId}")
    public ResponseEntity<Object> getCategory(@PathVariable @Positive Integer catId) {
        return publicCategoryServiceImpl.getCategory(catId);
    }
}
