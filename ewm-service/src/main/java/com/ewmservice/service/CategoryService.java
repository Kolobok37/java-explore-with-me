package com.ewmservice.service;

import com.ewmservice.Paging;
import com.ewmservice.exception.DeleteCompilationException;
import com.ewmservice.exception.NotFoundException;
import com.ewmservice.model.Category;
import com.ewmservice.model.Event;
import com.ewmservice.storage.CategoryStorage;
import com.ewmservice.storage.EventStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    CategoryStorage categoryStorage;
    @Autowired
    EventStorage eventStorage;

    public ResponseEntity<Object> createCategory(Category category) {
        return new ResponseEntity<>(categoryStorage.createCategory(category), HttpStatus.CREATED);
    }

    public ResponseEntity<Object> updateCategory(Category category, Integer categoryId) {
        Category oldCategory = categoryStorage.getCategory(categoryId)
                .orElseThrow(() -> new NotFoundException(String.format("Category with id=%d was not found",
                        categoryId)));
        oldCategory.setName(category.getName());
        return new ResponseEntity<>(categoryStorage.updateCategory(oldCategory), HttpStatus.OK);
    }

    public ResponseEntity<Object> deleteCategory(Integer categoriesId) {
        Integer[] catId = new Integer[]{categoriesId};
        List<Event> events = eventStorage.getEventsByAdmin(null, catId,
                LocalDateTime.of(1400, 1, 1, 0, 0),
                LocalDateTime.of(9999, 12, 31, 23, 59), Pageable.unpaged());
        if (!events.isEmpty()) {
            throw new DeleteCompilationException("There are related events");
        }
        categoryStorage.deleteCategory(categoriesId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<Object> getCategories(Integer from, Integer size) {
        return new ResponseEntity<>(categoryStorage.getCategories(Paging.paging(from, size)), HttpStatus.OK);
    }

    public ResponseEntity<Object> getCategory(Integer catId) {
        Category category = categoryStorage.getCategory(catId)
                .orElseThrow(() -> new NotFoundException(String.format("Category with id=%d was not found", catId)));
        return new ResponseEntity<>(category, HttpStatus.OK);
    }
}
