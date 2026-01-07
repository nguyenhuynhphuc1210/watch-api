package com.example.watch.controller;

import com.example.watch.dto.request.ProductRequestDTO;
import com.example.watch.dto.response.ProductResponseDTO;
import com.example.watch.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // ================= CREATE =================
    @PostMapping
    public ProductResponseDTO create(@RequestBody ProductRequestDTO dto) {
        return productService.create(dto);
    }

    // ================= READ ALL =================
    @GetMapping
    public List<ProductResponseDTO> getAll(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long brandId,
            @RequestParam(required = false) String keyword) {
        if (categoryId != null && brandId != null && keyword != null) {
            return productService.search(categoryId, brandId, keyword);
        }

        if (categoryId != null && keyword != null) {
            return productService.searchByCategory(categoryId, keyword);
        }

        if (brandId != null && keyword != null) {
            return productService.searchByBrand(brandId, keyword);
        }

        if (keyword != null) {
            return productService.search(keyword);
        }

        if (categoryId != null && brandId != null) {
            return productService.getByCategoryAndBrand(categoryId, brandId);
        }

        if (categoryId != null) {
            return productService.getByCategory(categoryId);
        }

        if (brandId != null) {
            return productService.getByBrand(brandId);
        }

        return productService.getAll();
    }

    // ================= READ BY ID =================
    @GetMapping("/{id}")
    public ProductResponseDTO getById(@PathVariable Long id) {
        return productService.getById(id);
    }

    // ================= UPDATE =================
    @PutMapping("/{id}")
    public ProductResponseDTO update(
            @PathVariable Long id,
            @RequestBody ProductRequestDTO dto) {
        return productService.update(id, dto);
    }

    // ================= DELETE =================
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }
}
