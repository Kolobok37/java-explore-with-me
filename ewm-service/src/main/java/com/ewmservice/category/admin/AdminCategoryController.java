package com.ewmservice.category.admin;

import com.ewmservice.category.Category;
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
public class AdminCategoryController {
    @Autowired
    AdminCategoryServiceImpl adminCategoryService;

    @PostMapping("/admin/categories")
    public ResponseEntity<Object> createCategory(@RequestBody @Valid Category category) {
        return adminCategoryService.createCategory(category);
    }

    @PatchMapping("/admin/categories/{categoriesId}")
    public ResponseEntity<Object> updateCategory(@RequestBody @Valid Category category,
                                                 @PathVariable @Positive Integer categoriesId) {
        return adminCategoryService.updateCategory(category, categoriesId);
    }

    @DeleteMapping("/admin/categories/{categoriesId}")
    public ResponseEntity<Object> deleteCategory(@PathVariable @Positive Integer categoriesId) {
        return adminCategoryService.deleteCategory(categoriesId);
    }


}
