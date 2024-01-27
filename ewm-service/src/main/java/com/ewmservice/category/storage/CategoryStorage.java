package com.ewmservice.category.storage;

import com.ewmservice.category.Category;
import com.ewmservice.exception.DuplicateDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CategoryStorage {
    @Autowired
    CategoryRepository categoryRepository;

    public Category createCategory(Category category) {
        try {
            return categoryRepository.save(category);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateDataException("Category name is not unique.");
        }
    }

    public Category updateCategory(Category category) {
        try {
            return categoryRepository.save(category);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateDataException("Category name is not unique.");
        }
    }

    public void deleteCategory(Integer categoriesId) {
        categoryRepository.deleteById(categoriesId);
    }

    public List<Category> getCategories(Pageable paging) {
        return categoryRepository.findAll(paging).toList();
    }

    public Optional<Category> getCategory(Integer catId) {
        return categoryRepository.findById(catId);
    }

}
