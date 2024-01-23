package com.ewmservice.dto.mappers;

import com.ewmservice.dto.CategoryDto;
import com.ewmservice.model.Category;

public class MapperCategory {
    public static CategoryDto mapToCategoryDto(Category category) {
        return new CategoryDto(category.getId(), category.getName());
    }
}
