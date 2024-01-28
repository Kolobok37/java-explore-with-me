package com.ewmservice.category.admin;

import com.ewmservice.category.Category;
import org.springframework.http.ResponseEntity;

public interface AdminCategoryService {
    public ResponseEntity<Object> createCategory(Category category);

    public ResponseEntity<Object> updateCategory(Category category, Integer categoryId);

    public ResponseEntity<Object> deleteCategory(Integer categoriesId);
}
