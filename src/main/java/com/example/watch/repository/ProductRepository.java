package com.example.watch.repository;

import com.example.watch.entity.Product;
import com.example.watch.enums.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ProductRepository
                extends JpaRepository<Product, Long>,
                JpaSpecificationExecutor<Product> {

        List<Product> findByStatus(ProductStatus status);

        List<Product> findByCategoryId(Long categoryId);

        List<Product> findByBrandId(Long brandId);

        List<Product> findByCategoryIdAndBrandId(Long categoryId, Long brandId);

        List<Product> findByNameContainingIgnoreCase(String keyword);

        List<Product> findByCategoryIdAndNameContainingIgnoreCase(
                        Long categoryId, String keyword);

        List<Product> findByBrandIdAndNameContainingIgnoreCase(
                        Long brandId, String keyword);

        List<Product> findByCategoryIdAndBrandIdAndNameContainingIgnoreCase(
                        Long categoryId, Long brandId, String keyword);
}
