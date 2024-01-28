package com.ewmservice.category.admin;

import com.ewmservice.category.Category;
import com.ewmservice.category.storage.CategoryStorage;
import com.ewmservice.event.Event;
import com.ewmservice.event.storage.EventStorage;
import com.ewmservice.exception.DeleteCompilationException;
import com.ewmservice.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdminCategoryServiceImpl {
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
}
