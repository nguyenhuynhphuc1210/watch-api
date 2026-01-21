package com.example.watch.controller;

import com.example.watch.dto.request.ProductRequestDTO;
import com.example.watch.dto.response.ProductResponseDTO;
import com.example.watch.service.ProductService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /* ================= CREATE ================= */

    @PostMapping
    public ProductResponseDTO create(@RequestBody ProductRequestDTO dto) {
        return productService.create(dto);
    }

    /* ================= READ ALL / FILTER / SEARCH ================= */

    @GetMapping
    public List<ProductResponseDTO> getAll(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long brandId,
            @RequestParam(required = false) String keyword) {

        // ===== FULL FILTER =====
        if (categoryId != null && brandId != null && keyword != null) {
            return productService.search(categoryId, brandId, keyword);
        }

        // ===== CATEGORY + SEARCH =====
        if (categoryId != null && keyword != null) {
            return productService.searchByCategory(categoryId, keyword);
        }

        // ===== BRAND + SEARCH =====
        if (brandId != null && keyword != null) {
            return productService.searchByBrand(brandId, keyword);
        }

        // ===== SEARCH =====
        if (keyword != null) {
            return productService.search(keyword);
        }

        // ===== CATEGORY + BRAND =====
        if (categoryId != null && brandId != null) {
            return productService.getByCategoryAndBrand(categoryId, brandId);
        }

        // ===== CATEGORY =====
        if (categoryId != null) {
            return productService.getByCategory(categoryId);
        }

        // ===== BRAND =====
        if (brandId != null) {
            return productService.getByBrand(brandId);
        }

        // ===== ALL =====
        return productService.getAll();
    }

    /* ================= READ DETAIL ================= */

    @GetMapping("/{id}")
    public ProductResponseDTO getById(@PathVariable Long id) {
        return productService.getById(id);
    }

    /* ================= UPDATE ================= */

    @PutMapping("/{id}")
    public ProductResponseDTO update(
            @PathVariable Long id,
            @RequestBody ProductRequestDTO dto) {
        return productService.update(id, dto);
    }

    /* ================= DELETE ================= */

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
