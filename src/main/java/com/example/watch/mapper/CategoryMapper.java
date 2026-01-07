package com.example.watch.mapper;

import com.example.watch.dto.request.CategoryRequestDTO;
import com.example.watch.dto.response.CategoryResponseDTO;
import com.example.watch.entity.Category;

public class CategoryMapper {

    public static CategoryResponseDTO toDTO(Category category) {
        return new CategoryResponseDTO(
                category.getId(),
                category.getName(),
                category.getDescription()
        );
    }

    public static Category toEntity(CategoryRequestDTO dto) {
        Category category = new Category();
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        return category;
    }
}
