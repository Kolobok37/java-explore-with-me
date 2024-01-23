package com.ewmservice.controller;

import com.ewmservice.model.Category;
import com.ewmservice.service.CategoryService;
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
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @PostMapping("/admin/categories")
    public ResponseEntity<Object> createCategory(@RequestBody @Valid Category category) {
        return categoryService.createCategory(category);
    }

    @PatchMapping("/admin/categories/{categoriesId}")
    public ResponseEntity<Object> updateCategory(@RequestBody @Valid Category category,
                                                 @PathVariable @Positive Integer categoriesId) {
        return categoryService.updateCategory(category, categoriesId);
    }

    @DeleteMapping("/admin/categories/{categoriesId}")
    public ResponseEntity<Object> deleteUser(@PathVariable @Positive Integer categoriesId) {
        return categoryService.deleteCategory(categoriesId);
    }

    @GetMapping("/categories")
    public ResponseEntity<Object> getCategories(@RequestParam(defaultValue = "0") Integer from,
                                                @RequestParam(defaultValue = "10") Integer size) {
        return categoryService.getCategories(from, size);
    }

    @GetMapping("/categories/{catId}")
    public ResponseEntity<Object> getCategory(@PathVariable @Positive Integer catId) {
        return categoryService.getCategory(catId);
    }
}
