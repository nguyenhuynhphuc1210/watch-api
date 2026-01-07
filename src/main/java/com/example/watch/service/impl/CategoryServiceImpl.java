package com.example.watch.service.impl;

import com.example.watch.dto.request.CategoryRequestDTO;
import com.example.watch.dto.response.CategoryResponseDTO;
import com.example.watch.entity.Category;
import com.example.watch.mapper.CategoryMapper;
import com.example.watch.repository.CategoryRepository;
import com.example.watch.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryResponseDTO create(CategoryRequestDTO dto) {
        Category category = CategoryMapper.toEntity(dto);
        return CategoryMapper.toDTO(categoryRepository.save(category));
    }

    @Override
    public List<CategoryResponseDTO> getAll() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryMapper::toDTO)
                .toList();
    }

    @Override
    public CategoryResponseDTO update(Long id, CategoryRequestDTO dto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        category.setName(dto.getName());
        return CategoryMapper.toDTO(categoryRepository.save(category));
    }

    @Override
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }
}
