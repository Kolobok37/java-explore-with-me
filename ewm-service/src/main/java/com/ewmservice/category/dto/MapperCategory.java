package com.ewmservice.category.dto;

import com.ewmservice.category.Category;

public class MapperCategory {
    public static CategoryDto mapToCategoryDto(Category category) {
        return new CategoryDto(category.getId(), category.getName());
    }
}
