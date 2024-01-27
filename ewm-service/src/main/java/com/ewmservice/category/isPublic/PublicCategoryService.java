package com.ewmservice.category.isPublic;

import org.springframework.http.ResponseEntity;

public interface PublicCategoryService {
    ResponseEntity<Object> getCategories(Integer from, Integer size);

    ResponseEntity<Object> getCategory(Integer catId);
}
