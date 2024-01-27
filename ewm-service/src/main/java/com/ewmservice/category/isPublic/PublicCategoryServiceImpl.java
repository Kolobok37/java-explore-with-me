package com.ewmservice.category.isPublic;

import com.ewmservice.Paging;
import com.ewmservice.category.Category;
import com.ewmservice.category.storage.CategoryStorage;
import com.ewmservice.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PublicCategoryServiceImpl implements PublicCategoryService {
    @Autowired
    CategoryStorage categoryStorage;

    public ResponseEntity<Object> getCategories(Integer from, Integer size) {
        return new ResponseEntity<>(categoryStorage.getCategories(Paging.paging(from, size)), HttpStatus.OK);
    }

    public ResponseEntity<Object> getCategory(Integer catId) {
        Category category = categoryStorage.getCategory(catId)
                .orElseThrow(() -> new NotFoundException(String.format("Category with id=%d was not found", catId)));
        return new ResponseEntity<>(category, HttpStatus.OK);
    }
}
