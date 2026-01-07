package com.example.watch.controller;

import com.example.watch.dto.request.CategoryRequestDTO;
import com.example.watch.dto.response.CategoryResponseDTO;
import com.example.watch.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    // CREATE
    @PostMapping
    public CategoryResponseDTO create(@RequestBody CategoryRequestDTO dto) {
        return categoryService.create(dto);
    }

    // READ - GET ALL
    @GetMapping
    public List<CategoryResponseDTO> getAll() {
        return categoryService.getAll();
    }

    // UPDATE
    @PutMapping("/{id}")
    public CategoryResponseDTO update(
            @PathVariable Long id,
            @RequestBody CategoryRequestDTO dto) {
        return categoryService.update(id, dto);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        categoryService.delete(id);
    }
}
