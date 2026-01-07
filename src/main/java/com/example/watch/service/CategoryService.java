package com.example.watch.service;

import com.example.watch.dto.request.CategoryRequestDTO;
import com.example.watch.dto.response.CategoryResponseDTO;
import java.util.List;

public interface CategoryService {
    CategoryResponseDTO create(CategoryRequestDTO dto);
    List<CategoryResponseDTO> getAll();
    CategoryResponseDTO update(Long id, CategoryRequestDTO dto);
    void delete(Long id);
}
