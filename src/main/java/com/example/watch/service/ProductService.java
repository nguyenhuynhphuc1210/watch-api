package com.example.watch.service;

import com.example.watch.dto.request.ProductRequestDTO;
import com.example.watch.dto.response.ProductResponseDTO;
import java.util.List;

public interface ProductService {
    ProductResponseDTO create(ProductRequestDTO dto);
    List<ProductResponseDTO> getAll();
    List<ProductResponseDTO> getByCategory(Long categoryId);
    List<ProductResponseDTO> getByBrand(Long brandId);
    List<ProductResponseDTO> getByCategoryAndBrand(Long categoryId, Long brandId);
    List<ProductResponseDTO> search(String keyword);
    List<ProductResponseDTO> searchByCategory(Long categoryId, String keyword);
    List<ProductResponseDTO> searchByBrand(Long brandId, String keyword);
    List<ProductResponseDTO> search(Long categoryId, Long brandId, String keyword);
    ProductResponseDTO getById(Long id);
    ProductResponseDTO update(Long id, ProductRequestDTO dto);
    void delete(Long id);
}
