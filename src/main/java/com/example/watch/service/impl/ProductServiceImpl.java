package com.example.watch.service.impl;

import com.example.watch.dto.request.ProductRequestDTO;
import com.example.watch.dto.response.ProductResponseDTO;
import com.example.watch.entity.Brand;
import com.example.watch.entity.Category;
import com.example.watch.entity.Product;
import com.example.watch.mapper.ProductMapper;
import com.example.watch.repository.BrandRepository;
import com.example.watch.repository.CategoryRepository;
import com.example.watch.repository.ProductRepository;
import com.example.watch.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;

    // ================= CREATE =================
    @Override
    public ProductResponseDTO create(ProductRequestDTO dto) {

        Brand brand = brandRepository.findById(dto.getBrandId())
                .orElseThrow(() -> new RuntimeException("Brand not found"));

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Product product = ProductMapper.toEntity(dto, brand, category);
        product = productRepository.save(product);

        return ProductMapper.toDTO(product);
    }

    // ================= READ ALL =================
    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> getAll() {
        return productRepository.findAll()
                .stream()
                .map(ProductMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> getByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId)
                .stream()
                .map(ProductMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> getByBrand(Long brandId) {
        return productRepository.findByBrandId(brandId)
                .stream()
                .map(ProductMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> getByCategoryAndBrand(Long categoryId, Long brandId) {
        return productRepository.findByCategoryIdAndBrandId(categoryId, brandId)
                .stream()
                .map(ProductMapper::toDTO)
                .toList();
    }

    public List<ProductResponseDTO> search(String keyword) {
        return productRepository
                .findByNameContainingIgnoreCase(keyword)
                .stream()
                .map(ProductMapper::toDTO)
                .toList();
    }

    public List<ProductResponseDTO> searchByCategory(Long categoryId, String keyword) {
        return productRepository
                .findByCategoryIdAndNameContainingIgnoreCase(categoryId, keyword)
                .stream()
                .map(ProductMapper::toDTO)
                .toList();
    }

    public List<ProductResponseDTO> searchByBrand(Long brandId, String keyword) {
        return productRepository
                .findByBrandIdAndNameContainingIgnoreCase(brandId, keyword)
                .stream()
                .map(ProductMapper::toDTO)
                .toList();
    }

    public List<ProductResponseDTO> search(Long categoryId, Long brandId, String keyword) {
        return productRepository
                .findByCategoryIdAndBrandIdAndNameContainingIgnoreCase(
                        categoryId, brandId, keyword)
                .stream()
                .map(ProductMapper::toDTO)
                .toList();
    }

    // ================= READ BY ID =================
    @Override
    @Transactional(readOnly = true)
    public ProductResponseDTO getById(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return ProductMapper.toDTO(product);
    }

    // ================= UPDATE =================
    @Override
    public ProductResponseDTO update(Long id, ProductRequestDTO dto) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Brand brand = brandRepository.findById(dto.getBrandId())
                .orElseThrow(() -> new RuntimeException("Brand not found"));

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        ProductMapper.updateEntity(product, dto, brand, category);

        product = productRepository.save(product);
        return ProductMapper.toDTO(product);
    }

    // ================= DELETE =================
    @Override
    public void delete(Long id) {

        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found");
        }

        productRepository.deleteById(id);
    }
}
